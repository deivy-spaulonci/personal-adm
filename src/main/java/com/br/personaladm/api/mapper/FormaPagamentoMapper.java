package com.br.personaladm.api.mapper;

import com.br.personaladm.api.dto.FormaPagamentoDTO;
import com.br.personaladm.domain.model.tipo.FormaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);
    FormaPagamento toModel(FormaPagamentoDTO formaPagamentoDTO);
    FormaPagamentoDTO toDTO(FormaPagamento formaPagamento);
    List<FormaPagamentoDTO> toDtoList(List<FormaPagamento> formaPagamentos);
}
