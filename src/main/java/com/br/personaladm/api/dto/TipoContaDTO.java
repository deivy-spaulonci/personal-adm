package com.br.personaladm.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoContaDTO {

    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 255)
    private String nome;

    private Boolean contaCartao;

    private Boolean ativa;
}
