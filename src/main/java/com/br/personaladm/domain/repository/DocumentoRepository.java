package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.Documento;
import com.br.personaladm.domain.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends GenericRepository<Documento> {
}
