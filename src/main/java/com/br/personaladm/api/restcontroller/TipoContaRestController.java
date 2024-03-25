package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.dto.TipoContaDTO;
import com.br.personaladm.api.mapper.TipoContaMapper;
import com.br.personaladm.business.service.TipoContaService;
import com.br.personaladm.domain.model.tipo.TipoConta;
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
@RequestMapping("/api/v1/tipo-conta")
public class TipoContaRestController {

    @Autowired
    private TipoContaService tipoContaService;

    public static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;

    @GetMapping("/{nome}")
    public ResponseEntity<?> get(@PathVariable("nome") String nome){
        List<TipoConta> retorno = tipoContaService.findByNome(nome);
        if(Objects.isNull(retorno) || retorno.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(/*Messages.getMessage("retorno.tipoconta.vazio")*/"nenhuma conta encontrada!");

        return ResponseEntity.ok(tipoContaMapper.toDtoList(retorno));
    }
    @GetMapping()
    public List<TipoContaDTO> get(){
        return tipoContaMapper.toDtoList(tipoContaService.findAll());
    }
}
