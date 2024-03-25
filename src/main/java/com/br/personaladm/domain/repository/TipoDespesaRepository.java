package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.tipo.TipoDespesa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDespesaRepository extends GenericRepository<TipoDespesa> {
    List<TipoDespesa> findTipoDespesaByNomeContainingIgnoreCaseOrderByNome(String nome);
}
