package com.br.personaladm.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Documento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_seq")
    @SequenceGenerator(name = "doc_seq", sequenceName = "doc_seq", allocationSize = 1)
    private Long id;

    @Column(length = 255, nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_TIPO_DOCUMENTO")
    private TipoDocumento tipoDocumento;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "doc_arquivos", joinColumns = @JoinColumn(name = "doc_id"))
    @Column(name = "arquivo", nullable = false)
    private List<String> doc_arquivos = new ArrayList<>();
}
