package com.br.personaladm.api.restcontroller;


import com.br.personaladm.api.dto.ContaDTO;
import com.br.personaladm.api.filter.ContaFilter;
import com.br.personaladm.api.mapper.ContaMapper;
import com.br.personaladm.business.service.ContaService;
import com.br.personaladm.domain.model.Conta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

//@Tag(name = "Contas", description = "contas APIs")
@RestController
@RequestMapping("/api/v1/conta")
public class ContaRestController {

    @Autowired
    ContaService contaService;

    private static final ContaMapper contaMapper = ContaMapper.INSTANCE;

    /**
     * listagem de contas por filtragem e tipo de ordenacao
     * @param contaFilter
     * @param sort
     * @return List<ContaDTO>
     */
    @Operation(
            summary = "Retorna lista de contas por filtro e ordenada",
            description = "Retorna lista de contas por objeto de filtro pre-definido, sem paginação e ordenada",
            tags = { "contas", "get" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping()
    public List<ContaDTO> get(@ModelAttribute ContaFilter contaFilter, Sort sort){
        return contaMapper.toDtoList(contaService.findAllContas(contaFilter, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> contaFindById(@PathVariable("id") Long id){
        Optional<Conta> conta = contaService.findContaPorId(id);
        return conta.<ResponseEntity<Object>>map(value -> ResponseEntity.ok(contaMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(/*Messages.getMessage("retorno.conta.vazio")*/"despesa nao econtrada"));
    }

    @GetMapping("/page")
    public Page<ContaDTO> getPage(@ModelAttribute ContaFilter contaFilter, Pageable pageable){
        return contaService.findAllContas(contaFilter, pageable).map(contaMapper::toDTO);
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getValorTotalConta(contaFilter);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContaDTO> create(@RequestBody @Valid ContaDTO contaDTO,
                                           UriComponentsBuilder uriBuilder){
        Conta conta = contaService.salvarConta(contaMapper.toModel(contaDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(contaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(contaMapper.toDTO(conta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid ContaDTO contaDTO){
        Optional<Conta> contaOptional = contaService.findContaPorId(contaDTO.getId());
        if(contaOptional.isPresent()){
            Conta conta = contaService.salvarConta(contaMapper.toModel(contaDTO));
            return ResponseEntity.ok(contaMapper.toDTO(conta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(/*Messages.getMessage("retono.conta.vazio")*/"conta nao econtrada");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Conta> conta = contaService.findContaPorId(id);
        if(conta.isPresent()){
            this.contaService.deleteContaPorId(conta.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(/*Messages.getMessage("retono.conta.vazio")*/"conta nao econtrada!");
    }

//    @GetMapping("/contaPorTipo")
//    public ResponseEntity<List<ContaPorTipoDTO>> getContaPorTipo(){
//        List<ContaPorTipoDTO> lista = new ArrayList<>();
//
//        List resultado = contaService.getContasPorTipo();
//
//        if(resultado.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        for(Object item : resultado){
//            ContaPorTipoDTO contaPorTipoDTO = new ContaPorTipoDTO();
//            var subitem = (Object[]) item;
//            TipoConta tipoConta = new TipoConta();
//            tipoConta.setId(Long.parseLong(subitem[0].toString()));
//            tipoConta.setNome(subitem[1].toString());
//            contaPorTipoDTO.setTipoConta(tipoConta);
//            contaPorTipoDTO.setValor(new BigDecimal(subitem[2].toString()));
//
//            lista.add(contaPorTipoDTO);
//        }
//
//        return ResponseEntity.ok(lista);
//    }
}
