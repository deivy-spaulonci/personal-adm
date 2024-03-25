package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends GenericRepository<Despesa> {
}
