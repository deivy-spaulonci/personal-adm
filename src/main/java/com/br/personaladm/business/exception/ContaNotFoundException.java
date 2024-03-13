package com.br.personaladm.business.exception;

public class ContaNotFoundException extends RuntimeException{
    public ContaNotFoundException(){
        super("Conta não encontrada!");
    }
}
