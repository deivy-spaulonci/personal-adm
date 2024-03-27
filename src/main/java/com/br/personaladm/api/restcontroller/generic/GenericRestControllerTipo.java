package com.br.personaladm.api.restcontroller.generic;

import com.br.personaladm.business.service.generic.GenericServiceTipo;
import com.br.personaladm.domain.model.tipo.generic.GenericEntityTipo;
import com.br.personaladm.domain.repository.generic.GenericReporitoryTipo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericRestControllerTipo<T extends GenericEntityTipo<T>>{
    private final GenericServiceTipo<T> service;

    public GenericRestControllerTipo(GenericReporitoryTipo<T> repository) {
        this.service = new GenericServiceTipo<T>(repository) {};
    }
    @GetMapping("")
    public ResponseEntity<List<T>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<T>> findByNome(@PathVariable("nome") String nome){
        return ResponseEntity.ok(service.findByNome(nome));
    }
    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }
    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }
    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created){
        return ResponseEntity.ok(service.create(created));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable Long id){
//        service.delete(id);
//        return ResponseEntity.ok("Ok");
//    }
//    @GetMapping("")
//    public ResponseEntity<Page<T>> getPage(Pageable pageable){
//        return ResponseEntity.ok(service.getPage(pageable));
//    }
}
