package com.br.personaladm.business.exception;

import com.br.personaladm.api.config.Messages;

public class DespesaNotFound extends RuntimeException{
    public DespesaNotFound(){
        super(Messages.getMessage("retorno.despesa.vazio"));
    }
}
