package com.br.personaladm.domain.specs;

import com.br.personaladm.business.filter.ContaFilter;
import com.br.personaladm.domain.model.Conta;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContaEspecification implements Specification<Conta> {
    private ContaFilter contaFilter;

    public ContaEspecification(ContaFilter contaFilter) {
        this.contaFilter = contaFilter;
    }

    @Override
    public Predicate toPredicate(Root<Conta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(contaFilter.id))
            predicates.add(criteriaBuilder.equal(root.get("id"), contaFilter.id));
        if (Objects.nonNull(contaFilter.getClass()))
            predicates.add(criteriaBuilder.equal(root.get("tipoConta"), contaFilter.tipoConta));

        if (Objects.nonNull(contaFilter.vencimentoInicial) && Objects.nonNull(contaFilter.vencimentoFinal))
            predicates.add(criteriaBuilder.between(root.get("vencimento"), contaFilter.vencimentoInicial, contaFilter.vencimentoFinal));
        if (Objects.nonNull(contaFilter.vencimentoInicial))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("vencimento"), contaFilter.vencimentoInicial));
        if (Objects.nonNull(contaFilter.vencimentoFinal))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("vencimento"), contaFilter.vencimentoFinal));

        if (Objects.nonNull(contaFilter.emissaoInicial) && Objects.nonNull(contaFilter.emissaoFinal))
            predicates.add(criteriaBuilder.between(root.get("emissao"), contaFilter.emissaoInicial, contaFilter.emissaoFinal));
        if (Objects.nonNull(contaFilter.emissaoInicial))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("emissao"), contaFilter.emissaoInicial));
        if (Objects.nonNull(contaFilter.emissaoFinal))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("emissao"), contaFilter.emissaoFinal));

        if(Objects.nonNull(contaFilter.status)){
            if(contaFilter.status>=-1 && contaFilter.status<=1){
                predicates.add(criteriaBuilder.isNull(root.get("formaPagamento")));
                switch (contaFilter.status){
                    case 1: predicates.add(criteriaBuilder.greaterThan(root.get("vencimento"), LocalDate.now())); break;
                    case 0: predicates.add(criteriaBuilder.equal(root.get("vencimento"), LocalDate.now())); break;
                    case -1: predicates.add(criteriaBuilder.lessThan(root.get("vencimento"), LocalDate.now())); break;
                }
            }else if(contaFilter.status==2)
                predicates.add(criteriaBuilder.isNotNull(root.get("formaPagamento")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
