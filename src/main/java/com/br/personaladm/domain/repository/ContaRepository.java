package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends GenericRepository<Conta> {
}
