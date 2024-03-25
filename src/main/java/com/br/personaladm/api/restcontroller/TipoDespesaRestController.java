package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.config.Messages;
import com.br.personaladm.api.dto.TipoDespesaDTO;
import com.br.personaladm.api.mapper.TipoDespesaMapper;
import com.br.personaladm.business.service.TipoDespesaService;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
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
@RequestMapping("/api/v1/tipo-despesa")
public class TipoDespesaRestController {

    @Autowired
    private TipoDespesaService tipoDespesaService;

    private static final TipoDespesaMapper tipoDespesaMapper = TipoDespesaMapper.INSTANCE;

    @GetMapping("/{nome}")
    public ResponseEntity<?> get(@PathVariable("nome") String nome){
        List<TipoDespesa> retorno = tipoDespesaService.findByNome(nome);
        if(Objects.isNull(retorno) || retorno.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("nenhum tipo econtrado!"/*Messages.getMessage("retorno.tipoconta.vazio")*/);
        return ResponseEntity.ok(tipoDespesaMapper.toDtoList(retorno));
    }

    @GetMapping()
    public List<TipoDespesaDTO> get(){
        return tipoDespesaMapper.toDtoList(tipoDespesaService.findAll());
    }

}
