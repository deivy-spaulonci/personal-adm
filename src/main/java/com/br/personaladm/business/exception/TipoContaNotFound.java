package com.br.personaladm.business.exception;

import com.br.personaladm.api.config.Messages;

public class TipoContaNotFound extends RuntimeException{
    public TipoContaNotFound(){
        super(Messages.getMessage("retorno.tipoconta.vazio"));
    }
}
