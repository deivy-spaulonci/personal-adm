package com.br.personaladm.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private Long id;

    @Size(min = 3)
    @NotBlank
    private String nome;

    @Size(min = 3)
    @NotBlank
    private String razao_social;

    private String cnpj;

    private String cidade_codigo_ibge;

}
