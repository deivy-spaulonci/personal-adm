package com.br.personaladm.api.dto;

import com.br.personaladm.domain.model.Fatura;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 255)
    private String nome;

    @NotNull
    private TipoDocumento tipoDocumento;

    private List<String> doc_arquivos;
}
