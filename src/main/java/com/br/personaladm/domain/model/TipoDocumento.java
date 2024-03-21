package com.br.personaladm.domain.model;

import jakarta.persistence.*;
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
public class TipoDocumento extends Tipo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_doc_seq")
    @SequenceGenerator(name = "tipo_doc_seq", sequenceName = "tipo_doc_seq", allocationSize = 1)
    private Long id;

    @Column(length = 255, nullable = false)
    private String nome;
}
