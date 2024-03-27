package com.br.personaladm.business.service.generic;

import com.br.personaladm.business.exception.TipoNotFound;
import com.br.personaladm.domain.model.tipo.generic.GenericEntityTipo;
import com.br.personaladm.domain.repository.generic.GenericReporitoryTipo;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class GenericServiceTipo<T extends GenericEntityTipo<T>>  {
    private final GenericReporitoryTipo<T> repository;

    public GenericServiceTipo(GenericReporitoryTipo<T> repository) {
        this.repository = repository;
    }
    public List<T> findAll(){
        return repository.findAll();
    }

    public List<T> findByNome(String nome){
        return repository.findTByNomeContainingIgnoreCaseOrderByNome(nome);
    }
    public T get(Long id){
        return repository.findById(id).orElseThrow(() -> new TipoNotFound());
    }
    @Transactional
    public T update(T updated){
        T dbDomain = get(updated.getId());
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }
    @Transactional
    public T create(T newDomain){
        T dbDomain = newDomain.createNewInstance();
        return repository.save(dbDomain);
    }
//    public Page<T> getPage(Pageable pageable){
//        return repository.findAll(pageable);
//    }
//    @Transactional
//    public void delete(Long id){
//        //check if object with this id exists
//        get(id);
//        repository.deleteById(id);
//    }
}
