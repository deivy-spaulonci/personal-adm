package com.br.personaladm.api.mapper;

import com.br.personaladm.api.dto.TipoContaDTO;
import com.br.personaladm.domain.model.tipo.TipoConta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);
    TipoConta toModel(TipoContaDTO tipoContaDTO);
    TipoContaDTO toDTO(TipoConta tipoConta);
    List<TipoContaDTO> toDtoList(List<TipoConta> tiposConta);

}
