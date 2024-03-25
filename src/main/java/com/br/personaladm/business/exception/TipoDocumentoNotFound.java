package com.br.personaladm.business.exception;

public class TipoDocumentoNotFound extends RuntimeException{
    public TipoDocumentoNotFound(){
        super("Tipo documento nao econtrado!");
    }
}
