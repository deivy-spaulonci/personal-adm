package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.dto.TipoContaDTO;
import com.br.personaladm.api.dto.TipoDocumentoDTO;
import com.br.personaladm.api.mapper.TipoContaMapper;
import com.br.personaladm.api.mapper.TipoDocumentoMapper;
import com.br.personaladm.business.service.TipoContaService;
import com.br.personaladm.business.service.TipoDocumentoService;
import com.br.personaladm.domain.model.tipo.TipoConta;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tipo-documento")
public class TipoDocumentoRestController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    public static final TipoDocumentoMapper tipoDocumentoMapper = TipoDocumentoMapper.INSTANCE;

    @GetMapping("/{nome}")
    public ResponseEntity<?> get(@PathVariable("nome") String nome){
        List<TipoDocumento> retorno = tipoDocumentoService.findByNome(nome);
        if(Objects.isNull(retorno) || retorno.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(/*Messages.getMessage("retorno.tipoconta.vazio")*/"nenhuma conta encontrada!");

        return ResponseEntity.ok(tipoDocumentoMapper.toDtoList(retorno));
    }
    @GetMapping()
    public List<TipoDocumentoDTO> get(){
        return tipoDocumentoMapper.toDtoList(tipoDocumentoService.findAll());
    }
}
