package com.br.personaladm.api.filter;

import com.br.personaladm.domain.model.ContaStatus;
import com.br.personaladm.domain.model.tipo.TipoConta;

import java.time.LocalDate;

public record ContaFilter(Long id,
                          TipoConta tipoConta,
                          LocalDate vencimentoInicial,
                          LocalDate vencimentoFinal,
                          LocalDate emissaoInicio,
                          LocalDate emissaoTermino,
                          ContaStatus contaStatus) {

    public ContaFilter(Long id,
                       TipoConta tipoConta,
                       LocalDate vencimentoInicial,
                       LocalDate vencimentoFinal,
                       LocalDate emissaoInicio,
                       LocalDate emissaoTermino,
                       ContaStatus contaStatus){
        this.id = id;
        this.tipoConta = tipoConta;
        this.vencimentoInicial = vencimentoInicial;
        this.vencimentoFinal = vencimentoFinal;
        this.emissaoInicio = emissaoInicio;
        this.emissaoTermino = emissaoTermino;
        this.contaStatus = contaStatus;
    }
}
