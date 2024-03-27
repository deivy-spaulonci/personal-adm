package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.restcontroller.generic.GenericRestControllerTipo;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
import com.br.personaladm.domain.repository.TipoDespesaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipo-despesa")
public class TipoDespesaRestController extends GenericRestControllerTipo<TipoDespesa> {
    public TipoDespesaRestController(TipoDespesaRepository tipoDespesaRepository){
        super(tipoDespesaRepository);
    }
}
