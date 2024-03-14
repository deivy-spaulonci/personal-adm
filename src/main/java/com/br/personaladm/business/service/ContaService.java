package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.ContaNotFoundException;
import com.br.personaladm.business.filter.ContaFilter;
import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.repository.ContaRepository;
import com.br.personaladm.domain.specs.ContaEspecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ContaRepository contaRepository;

    /**
     * salva nova conta
     * @param Conta
     * @return Conta
     */
    public Conta salvarConta(Conta conta){
        return Optional.ofNullable(contaRepository.save(conta))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar a conta!"));
    }

    /**
     * busca conta por id
     * @param id
     * @return Optional<Conta>
     */
    public Optional<Conta> findContaPorId(Long id){
        return Optional.ofNullable(contaRepository.findById(id))
                .orElseThrow(()-> new ContaNotFoundException());
    }

    /**
     * apaga conta por id
     * @param id
     */
    public boolean deleteContaPorId(Long id){
        Optional<Conta> conta = findContaPorId(id);
        if(conta.isPresent())
            contaRepository.delete(conta.get());
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
        return contaRepository.findAll(pageable);
    }

    /**
     * busca contas filtradas e ordenadas
     * @param contaFilter
     * @param sort
     * @return List<Contas>
     */
    public List<Conta> findAllContas(ContaFilter contaFilter, Sort sort){
        var contaSpecification = new ContaEspecification(contaFilter);
        return contaRepository.findAll();
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


}
