package com.br.personaladm.business.service;

import com.br.personaladm.api.filter.DespesaFilter;
import com.br.personaladm.domain.model.Despesa;
import com.br.personaladm.domain.repository.DespesaRepository;
import com.br.personaladm.domain.repository.FornecedorRepository;
import com.br.personaladm.domain.specs.DespesaSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * class service Despesas
 */
@Service
public class DespesaService {

    private static final Logger log = LoggerFactory.getLogger(DespesaService.class);

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * lista despesas por filtro e ordenado
     * @param despesaFilter
     * @param sort
     * @return List<Despesa>
     */
    public List<Despesa> searchByFilterOrder(DespesaFilter despesaFilter, Sort sort){
        DespesaSpecification despesaSpecification = new DespesaSpecification(despesaFilter);
        return despesaRepository.findAll(despesaSpecification, sort);
    }
    /**
     * lista despesa por filtro com paginacao
     * @param despesaFilter
     * @param pageable
     * @return Page<Despesa>
     */
    public Page<Despesa> getPageByFilters(DespesaFilter despesaFilter, Pageable pageable){
        DespesaSpecification despesaSpecification = new DespesaSpecification(despesaFilter);
        return despesaRepository.findAll(despesaSpecification, pageable);
    }
    /**
     * busca despesa por id
     * @param id
     * @return Despesa
     */
    public Optional<Despesa> findById(Long id){
        return despesaRepository.findById(id);
    }
    /**
     * salvar despesa
     * @param despesa
     * @return Despesas
     */
    public Despesa save(Despesa despesa){
        return despesaRepository.save(despesa);
    }
    /**
     * atualiza a despesa
     * @param despesa
     * @return
     */
    public Despesa update(Despesa despesa){
        findById(despesa.getId());
        return despesaRepository.save(despesa);
    }
    /**
     * excluir a despesa
     * @param id
     */
    public void deleteById(Long id){
        findById(id);
        despesaRepository.deleteById(id);
    }

    /**
     *  somatoria do campo valor com filtros
     * @param despesaFilter
     * @return BigDecimal
     */
    public BigDecimal getValorTotalDespesa(DespesaFilter despesaFilter) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);
        DespesaSpecification despesaSpecification = new DespesaSpecification(despesaFilter);
        query.multiselect(cb.sum(root.get("valor")));
        query.where(despesaSpecification.toPredicate(root, query, cb));
        Query qry = em.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    /**
     *  salvar lista de importacao de desepsas
     * @param  lista de despesas
     * @return BigDecimal
     */
    public int salvarImportacaoDespesas(List<Despesa> lista){
        return despesaRepository.saveAll(lista).size();
    }

    public List getDespesaPorTipo() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);
        query.multiselect(
                root.get("tipoDespesa"),
                cb.sum(root.get("valor")));
        query.groupBy(root.get("tipoDespesa"));
        Query qry = em.createQuery(query);
        return qry.getResultList();
    }

//    public String importFromFile(File file){
//        StringBuilder resposta = new StringBuilder();
//        Integer linha = 0;
//        List<Despesa> lista = new ArrayList<>();
//        try {
//            String[] HEADERS = {"cnpj","data","valor","tipo","forma","obs"};
//            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                    .setHeader(HEADERS)
//                    .setDelimiter(";")
//                    .setSkipHeaderRecord(true)
//                    .build();
//
//            CSVParser csvParser =  CSVParser.parse(file, StandardCharsets.UTF_8,csvFormat);
//            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//            for (CSVRecord csvRecord : csvRecords) {
//                Validator val = CNPJValidator::isValidCNPJ;
//                Optional<Fornecedor> fornecedor = null;
//
//                String cnpj = csvRecord.get("cnpj");
//                Optional<Boolean> cnpjValido =  val.isValid(cnpj);
//                if(cnpjValido.isEmpty() || !cnpjValido.get())
//                    resposta.append("Linha [").append(linha).append("] CNPJ inválido! <br/>");
//                else{
//                    fornecedor = fornecedorRepository.findFornecedorByCnpj(cnpj);
//                    if(fornecedor.isEmpty())
//                        resposta.append("Linha [").append(linha).append("] CNPJ não corresponde a nenhum Fornecedor! <br/>");
//                }
//
//                val = DateValidator::isValidData;
//                Optional<LocalDate> dataresult = val.isValid(csvRecord.get("data"));
//                if(dataresult.isEmpty())
//                    resposta.append("Linha [").append(linha).append("] Data inválida! <br/>");
//
//                val = ValorValidator::isValidValor;
//                Optional<BigDecimal> valorresult = val.isValid(csvRecord.get("valor"));
//                if(valorresult.isEmpty())
//                    resposta.append("Linha [").append(linha).append("] Valor inválido!<br/>");
//
//                String tipo = csvRecord.get("tipo").toUpperCase();
//                TipoDespesa[]  tipos = TipoDespesa.values();
//                if(!Arrays.asList(tipos).contains(TipoDespesa.valueOf(tipo)))
//                    resposta.append("Linha [").append(linha).append("] Tipo Despesa inválido!<br/>");
//
//                String forma = csvRecord.get("forma").toUpperCase();
//                FormaPagamento[] formas = FormaPagamento.values();
//                if(!Arrays.asList(formas).contains(FormaPagamento.valueOf(forma)))
//                    resposta.append("Linha [").append(linha).append("] Froma Pagamento inválido!<br/>");
//
//                if(resposta.isEmpty()){
//                    Despesa despesa = Despesa.builder()
//                            .tipoDespesa(TipoDespesa.valueOf(tipo))
//                            .data(dataresult.get())
//                            .valor(valorresult.get())
//                            .formaPagamento(FormaPagamento.valueOf(forma))
//                            .obs(csvRecord.get("obs"))
//                            .fornecedor(fornecedor.get())
//                            .build();
//
//                    lista.add(despesa);
//                }
//
//            }
//            csvParser.close();
//            return resposta.toString();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
