package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.tipo.TipoConta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoContaRepository extends GenericRepository<TipoConta> {
    List<TipoConta> findTipoContasByNomeContainingIgnoreCaseOrderByNome(String nome);
}