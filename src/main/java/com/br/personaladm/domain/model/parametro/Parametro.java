package com.br.personaladm.domain.model.parametro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parametro implements Serializable {
    @Id
    private Long id;
    private String chave;
    private String valor;
}
