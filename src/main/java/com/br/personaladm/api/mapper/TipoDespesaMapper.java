package com.br.personaladm.api.mapper;

import com.br.personaladm.api.dto.TipoDespesaDTO;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoDespesaMapper {
    TipoDespesaMapper INSTANCE = Mappers.getMapper(TipoDespesaMapper.class);
    TipoDespesa toModel(TipoDespesaDTO tipoDespesaDTO);
    TipoDespesaDTO toDTO(TipoDespesa tipoDespesa);
    List<TipoDespesaDTO> toDtoList(List<TipoDespesa> tipoDespesas);
}
