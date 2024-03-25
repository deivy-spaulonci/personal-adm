package com.br.personaladm.business.exception;

import com.br.personaladm.api.config.Messages;

public class ContaNotFound extends RuntimeException{
    public ContaNotFound(){
        super(Messages.getMessage("retorno.conta.vazio"));
    }
}
