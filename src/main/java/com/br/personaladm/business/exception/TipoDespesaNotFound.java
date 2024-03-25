package com.br.personaladm.business.exception;

public class TipoDespesaNotFound extends RuntimeException{
    public TipoDespesaNotFound(){
        super("Tipo de despesa nao encontrado!");
    }
}
