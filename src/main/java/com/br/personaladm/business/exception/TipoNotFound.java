package com.br.personaladm.business.exception;

public class TipoNotFound extends RuntimeException{
    public TipoNotFound(){
        super("Tipo não econtrado!");
    }
}
