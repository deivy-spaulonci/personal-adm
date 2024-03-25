package com.br.personaladm.business.validation;

import java.util.Optional;

@FunctionalInterface
public interface Validator<T> {
    Optional<T> isValid(String cnpj);
}
