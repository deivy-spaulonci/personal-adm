package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.restcontroller.generic.GenericRestControllerTipo;
import com.br.personaladm.domain.model.tipo.FormaPagamento;
import com.br.personaladm.domain.repository.FormaPagamentoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoRestController extends GenericRestControllerTipo<FormaPagamento> {
    public FormaPagamentoRestController(FormaPagamentoRepository formaPagamentoRepository) {
        super(formaPagamentoRepository);
    }
}
