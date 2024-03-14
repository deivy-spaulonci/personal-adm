package com.br.personaladm.business.filter;

import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.model.TipoConta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaFilter implements Serializable {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("tipoConta")
    public TipoConta tipoConta;

    @JsonProperty("vencimentoInicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate vencimentoInicial;

    @JsonProperty("vencimentoFinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate vencimentoFinal;

    @JsonProperty("emissaoInicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate emissaoInicial;

    @JsonProperty("emissaoFinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate emissaoFinal;

    public Integer status;

}
