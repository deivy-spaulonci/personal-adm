package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.TipoContaNotFound;
import com.br.personaladm.domain.model.tipo.TipoConta;
import com.br.personaladm.domain.repository.TipoContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TipoContaService {
    @Autowired
    private TipoContaRepository tipoContaRepository;

    /**
     * lista tipo de contas ordenado pelo nome
     * @return List<TipoConta>
     */
    public List<TipoConta> findAll(){
        return tipoContaRepository.findAll(Sort.by("nome"));
    }
    /**
     * lista tipos de conta pelo nome
     * @param nome
     * @return List<TipoConta>
     */
    public List<TipoConta> findByNome(String nome){
        return tipoContaRepository.findTipoContasByNomeContainingIgnoreCaseOrderByNome(nome);
    }
    /**
     * busca tipo de conta pelo id
     * @param id
     * @return TipoConta
     */
    public TipoConta findById(Long id){
        return tipoContaRepository.findById(id).orElseThrow(TipoContaNotFound::new);
    }
    /**
     * atuliza o tipo conta
     * @param tipoConta
     */
    public TipoConta update(TipoConta tipoConta){
        findById(tipoConta.getId());
        return tipoContaRepository.save(tipoConta);
    }
    /**
     * salva tipo conta
     * @param tipoConta
     * @return TipoConta
     */
    public TipoConta save(TipoConta tipoConta){
        return tipoContaRepository.save(tipoConta);
    }
}
