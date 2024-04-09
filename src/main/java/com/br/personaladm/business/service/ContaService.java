package com.br.personaladm.business.service;

import com.br.personaladm.api.filter.ContaFilter;
import com.br.personaladm.business.exception.ContaNotFoundException;
import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.model.parametro.ChavePasta;
import com.br.personaladm.domain.model.parametro.SulFixoTipoArquivo;
import com.br.personaladm.domain.repository.ContaRepository;
import com.br.personaladm.domain.repository.ParametroRepository;
import com.br.personaladm.domain.specs.ContaEspecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContaService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private FileService fileService;

    /**
     * salva nova conta
     * @param conta
     * @return Conta
     */
    public Conta salvarConta(Conta conta){
        if(Objects.isNull(conta.getTitulo()) || conta.getTitulo().isEmpty())
            conta.setTitulo(criaNomeArquivoConta(conta, "", SulFixoTipoArquivo.TB));
        if(Objects.isNull(conta.getComprovante()) || conta.getComprovante().isEmpty())
            conta.setComprovante(criaNomeArquivoConta(conta, "", SulFixoTipoArquivo.CO));
        return Optional.ofNullable(contaRepository.save(conta))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar a conta!"));
    }

    /**
     * busca conta por id
     * @param id
     * @return Optional<Conta>
     */
    public Optional<Conta> findContaPorId(Long id){
        return Optional.of(contaRepository.findById(id))
                .orElseThrow(ContaNotFoundException::new);
    }

    /**
     * apaga conta por id
     * @param id
     */
    public boolean deleteContaPorId(Long id){
        Optional<Conta> conta = findContaPorId(id);
        if(conta.isPresent()){
            excluirArquivoTitulo(conta.get());
            contaRepository.delete(conta.get());
        }
        return contaRepository.findById(id) == null;
    }

    /**
     * busca contas paginadas e filtradas
     * @param contaFilter
     * @param pageable
     * @return Page<Conta>
     */
    public Page<Conta> findAllContas(ContaFilter contaFilter, Pageable pageable){
        var contaSpecification = new ContaEspecification(contaFilter);
        return contaRepository.findAll(contaSpecification, pageable);
    }

    /**
     * busca contas filtradas e ordenadas
     * @param contaFilter
     * @param sort
     * @return List<Contas>
     */
    public List<Conta> findAllContas(ContaFilter contaFilter, Sort sort){
        var contaSpecification = new ContaEspecification(contaFilter);
        return contaRepository.findAll(contaSpecification, sort);
    }

    public List<Conta> findAll(){
        return contaRepository.findAll(Sort.by("vencimento").descending());
    }

    /**
     *  somatoria do campo valor com filtros
     * @param contaFilter
     * @return BigDecimal
     */
    public BigDecimal getValorTotalConta(ContaFilter contaFilter){
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Conta> root = query.from(Conta.class);
        var contaSpecification = new ContaEspecification(contaFilter);
        query.multiselect(cb.sum(root.get("valor")));
        query.where(contaSpecification.toPredicate(root, query, cb));
        Query qry = em.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    /**
     * consulta contas por tipo id e nome
     * @return List
     */
    public List getContasPorTipo(){
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Conta> root = query.from(Conta.class);

        query.multiselect(
                root.get("tipoConta").get("id"),
                root.get("tipoConta").get("nome"),
                cb.sum(root.get("valor")));
        query.groupBy(
                root.get("tipoConta").get("id"),
                root.get("tipoConta").get("nome"));

        Query qry = em.createQuery(query);
        return qry.getResultList();
    }

    /**
     * exclui o arquivo do titulo/boleto pela conta
     * @param conta
     * @return Boolean
     */
    public Boolean excluirArquivoTitulo(Conta conta){
        try {
            fileService.excluirArquivo(FilenameUtils.getBaseName(conta.getTitulo())+".zip", ChavePasta.PASTA_TITULO);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * exclui o arquivo do comprovante pela conta
     * @param conta
     * @return Boolean
     */
    public Boolean excluirArquivoComprovante(Conta conta){
        try {
            fileService.excluirArquivo(conta.getComprovante(), ChavePasta.PASTA_COMPROVANTE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * crio num novo nome padrao dos sistema com a extensao que recebo
     * crio o aquivo compactado sem essa extencao e como zip
     * atualizo o conta com o nome do arquivo do comprovante
     * @param conta
     * @param multipartFile
     */
    public void uploadArquivoComprovante(Conta conta, MultipartFile multipartFile){
        String novoNomeComExtencao = criaNomeArquivoConta(conta,
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
                SulFixoTipoArquivo.CO);
        fileService.createFileCompacated(multipartFile, ChavePasta.PASTA_COMPROVANTE, novoNomeComExtencao);

        conta.setComprovante(novoNomeComExtencao);
        salvarConta(conta);
    }
    /**
     * crio num novo nome padrao dos sistema com a extensao que recebo
     * crio o aquivo compactado sem essa extencao e como zip
     * atualizo o conta com o nome do arquivo do titulo/boleto
     * @param conta
     * @param multipartFile
     */
    public void uploadArquivoTitulo(Conta conta, MultipartFile multipartFile){
        String novoNomeComExtencao = criaNomeArquivoConta(conta,
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
                SulFixoTipoArquivo.TB);
        fileService.createFileCompacated(multipartFile, ChavePasta.PASTA_TITULO, novoNomeComExtencao);

        conta.setTitulo(novoNomeComExtencao);
        salvarConta(conta);
    }

    /**
     * trago o arquivo de titulo referente a conta
     * @param idConta
     * @return Resource
     */
    public Resource downloadArquivoTitulo(Long idConta){
        Conta conta = findContaPorId(idConta).get();
        return fileService.getArquivo(conta.getTitulo(), ChavePasta.PASTA_TITULO);
    }

    /**
     * trago o arquivo do comprovante referente a conta
     * @param idConta
     * @return Resource
     */
    public Resource downloadArquivoComprovante(Long idConta){
        Conta conta = findContaPorId(idConta).get();
        return fileService.getArquivo(conta.getComprovante(), ChavePasta.PASTA_COMPROVANTE);
    }

    /**
     * crio um nome padrao para o arquivo
     * @param conta
     * @return String;
     */
    public String criaNomeArquivoConta(Conta conta,
                                         String extensao,
                                         SulFixoTipoArquivo sulFixoTipoArquvio){
        String novoNome="";
        novoNome = conta.getVencimento().getYear()+"";
        novoNome += "_"+String.format("%02d", conta.getVencimento().getMonth().getValue());
        novoNome += "_"+String.format("%06d", conta.getId());
        novoNome += "_"+String.format("%06d", conta.getTipoConta().getId());
        novoNome += sulFixoTipoArquvio.name();
        if(Objects.nonNull(extensao) && !extensao.trim().isEmpty())
            novoNome += "."+extensao;

        return novoNome;
    }



}
