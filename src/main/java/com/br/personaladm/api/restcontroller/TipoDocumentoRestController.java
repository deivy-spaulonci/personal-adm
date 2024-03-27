package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.restcontroller.generic.GenericRestControllerTipo;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import com.br.personaladm.domain.repository.TipoDocumentoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipo-documento")
public class TipoDocumentoRestController  extends GenericRestControllerTipo<TipoDocumento> {
    public TipoDocumentoRestController(TipoDocumentoRepository tipoDocumentoRepository) {
        super(tipoDocumentoRepository);
    }
}
