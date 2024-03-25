package com.br.personaladm.business.validation;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class ValorValidator {

    public static Optional<BigDecimal> isValidValor(String valor){
        try {
            if(Objects.isNull(valor) || valor.trim().isEmpty())
                return Optional.empty();

            BigDecimal valorDespesa = new BigDecimal(valor).movePointLeft(2);
            if(valorDespesa.compareTo(BigDecimal.ZERO)>0)
                return Optional.of(valorDespesa);
            return Optional.empty();
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
