package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.dto.FormaPagamentoDTO;
import com.br.personaladm.api.dto.TipoDespesaDTO;
import com.br.personaladm.api.mapper.FormaPagamentoMapper;
import com.br.personaladm.api.mapper.FornecedorMapper;
import com.br.personaladm.business.service.FormaPagamentoService;
import com.br.personaladm.domain.model.tipo.FormaPagamento;
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
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoRestController {
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    private static final FormaPagamentoMapper formaPagamentoMapper = FormaPagamentoMapper.INSTANCE;
    @GetMapping("/{nome}")
    public ResponseEntity<?> get(@PathVariable("nome") String nome){
        List<FormaPagamento> retorno = formaPagamentoService.findByNome(nome);
        if(Objects.isNull(retorno) || retorno.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("nenhum forma pagamento econtrado!"/*Messages.getMessage("retorno.tipoconta.vazio")*/);
        return ResponseEntity.ok(formaPagamentoMapper.toDtoList(retorno));
    }

    @GetMapping()
    public List<FormaPagamentoDTO> get(){
        return formaPagamentoMapper.toDtoList(formaPagamentoService.findAll());
    }

}
