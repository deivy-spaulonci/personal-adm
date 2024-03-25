package com.br.personaladm.business.exception;

import com.br.personaladm.api.config.Messages;

public class FornecedorNotFound extends RuntimeException{
    public FornecedorNotFound(){
        super(Messages.getMessage("retorno.fornecedor.vazio"));
    }
}
