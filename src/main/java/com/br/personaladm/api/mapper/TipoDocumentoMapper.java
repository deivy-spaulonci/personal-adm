package com.br.personaladm.api.mapper;

import com.br.personaladm.api.dto.TipoDocumentoDTO;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoDocumentoMapper {
    TipoDocumentoMapper INSTANCE = Mappers.getMapper(TipoDocumentoMapper.class);
    TipoDocumento toModel(TipoDocumentoDTO tipoDocumentoDTO);
    TipoDocumentoDTO toDTO(TipoDocumento tipoDocumento);
    List<TipoDocumentoDTO> toDtoList(List<TipoDocumento> tipoDocumentos);
}
