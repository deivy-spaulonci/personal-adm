package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.TipoDespesaNotFound;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
import com.br.personaladm.domain.repository.TipoDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDespesaService {

    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    /**
     * lista tipo de despesas ordenado pelo nome
     * @return List<TipoDespesa>
     */
    public List<TipoDespesa> findAll(){
        return tipoDespesaRepository.findAll(Sort.by("nome"));
    }
    /**
     * retorna lista de tipo de despesas pelo pelo nome
     * @param nome
     * @return List<TipoDespesa>
     */
    public List<TipoDespesa> findByNome(String nome){
        return tipoDespesaRepository.findTipoDespesaByNomeContainingIgnoreCaseOrderByNome(nome);
    }
    /**
     * busca tipo de despesa pelo id
     * @param id
     * @return TipoDespesa
     */
    public TipoDespesa findById(Long id){
        return tipoDespesaRepository.findById(id).orElseThrow(TipoDespesaNotFound::new);
    }
    /**
     * atuliza o tipo despesa
     * @param tipoDespesa
     * @return TipoDespesa
     */
    public TipoDespesa update(TipoDespesa tipoDespesa){
        findById(tipoDespesa.getId());
        return tipoDespesaRepository.save(tipoDespesa);
    }

    /**
     * salva tipo de despesa
     * @param tipoDespesa
     * @return TipoDespesa
     */
    public TipoDespesa save(TipoDespesa tipoDespesa){
        return tipoDespesaRepository.save(tipoDespesa);
    }
}
