package com.br.personaladm.domain.model.tipo.generic;

public interface GenericEntityTipo<T> {

    void update(T source);

    T createNewInstance();

    Long getId();
    String getNome();
}
