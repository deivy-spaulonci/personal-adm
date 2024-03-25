package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.tipo.TipoDocumento;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends GenericRepository<TipoDocumento> {
    List<TipoDocumento> findTipoContaByNomeContainsIgnoreCaseOrderByNome(String nome);
}
