package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.Despesa;
import com.br.personaladm.domain.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends GenericRepository<Despesa> {
}
