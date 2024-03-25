package com.br.personaladm.business.exception;

public class FormaPagamentoNotFound extends RuntimeException{
    public FormaPagamentoNotFound(){
        super("Forma de pagamento nao encontrado!");
    }
}
