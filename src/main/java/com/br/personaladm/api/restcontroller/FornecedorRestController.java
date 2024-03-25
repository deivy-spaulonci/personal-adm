package com.br.personaladm.api.restcontroller;

import com.br.personaladm.api.config.Messages;
import com.br.personaladm.api.dto.FornecedorDTO;
import com.br.personaladm.api.mapper.FornecedorMapper;
import com.br.personaladm.business.service.FornecedorService;
import com.br.personaladm.domain.model.Fornecedor;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fornecedor")
public class FornecedorRestController {

    @Autowired
    private FornecedorService fornecedorService;

    private static final FornecedorMapper fornecedorMapper = FornecedorMapper.INSTANCE;

    @GetMapping()
    public List<FornecedorDTO> get(@ModelAttribute String texto, Sort sort){
        return fornecedorMapper.toDtoList(fornecedorService.listFornecedorByNomeOrRazaoOrCNPJ(texto, sort));
    }
    @GetMapping("/page")
    public Page<FornecedorDTO> getPage(@ModelAttribute String texto, Pageable pageable){
        return fornecedorService.getPageByFilters(texto, pageable).map(fornecedorMapper::toDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> contaFindById(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        return fornecedor.<ResponseEntity<Object>>map(value -> ResponseEntity.ok(fornecedorMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Messages.getMessage("retorno.fornecedor.vazio")));
    }
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorDTO> create(@RequestBody @Valid FornecedorDTO fornecedorDTO,
                                           UriComponentsBuilder uriBuilder){
        Fornecedor fornecedor = fornecedorService.save(fornecedorMapper.toModel(fornecedorDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(fornecedorDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(fornecedorMapper.toDTO(fornecedor));
    }
    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid FornecedorDTO fornecedorDTO){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(fornecedorDTO.getId());
        if(fornecedorOptional.isPresent()){
            Fornecedor fornecedor = fornecedorService.update(fornecedorMapper.toModel(fornecedorDTO));
            return ResponseEntity.ok(fornecedorMapper.toDTO(fornecedor));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Messages.getMessage("retorno.fornecedor.vazio"));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(Messages.getMessage("permisao.excluir.fornecedor"));
    }

    @GetMapping(value = "/consultacnpj", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String consultaCNPJ(@RequestParam(name = "cnpj") String cnpj){
        String URL_API = "https://www.receitaws.com.br/v1/cnpj/"+cnpj;
        HttpURLConnection con = null;

        try {
            URL url = null;
            url = new URL(URL_API);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return getJson(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(URL url) {
        if (url == null)
            throw new RuntimeException("URL é null");

        String html = null;
        StringBuilder sB = new StringBuilder();
        try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((html = bR.readLine()) != null)
                sB.append(html);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sB.toString();
    }
}
