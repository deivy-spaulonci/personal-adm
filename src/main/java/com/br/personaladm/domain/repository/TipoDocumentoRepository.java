package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.tipo.TipoDocumento;
import com.br.personaladm.domain.repository.generic.GenericReporitoryTipo;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends GenericReporitoryTipo<TipoDocumento> {
}
