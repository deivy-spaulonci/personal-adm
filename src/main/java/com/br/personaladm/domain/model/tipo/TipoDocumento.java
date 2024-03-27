package com.br.personaladm.domain.model.tipo;

import com.br.personaladm.domain.model.tipo.generic.GenericEntityTipo;
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
public class TipoDocumento implements Serializable, GenericEntityTipo<TipoDocumento> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_doc_seq")
    @SequenceGenerator(name = "tipo_doc_seq", sequenceName = "tipo_doc_seq", allocationSize = 1)
    private Long id;

    @Column(length = 255, nullable = false)
    private String nome;

    @Override
    public void update(TipoDocumento source) {
        this.nome = source.getNome();
    }

    @Override
    public TipoDocumento createNewInstance() {
        TipoDocumento newInstance = new TipoDocumento();
        newInstance.update(this);
        return newInstance;
    }
}
