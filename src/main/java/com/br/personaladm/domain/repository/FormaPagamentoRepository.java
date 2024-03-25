package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.tipo.FormaPagamento;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends GenericRepository<FormaPagamento> {
    List<FormaPagamento> findFormaPagamentoByNomeContainingIgnoreCaseOrderByNome(String nome);
}
