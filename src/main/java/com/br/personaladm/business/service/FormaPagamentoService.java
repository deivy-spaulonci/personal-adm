package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.FormaPagamentoNotFound;
import com.br.personaladm.domain.model.tipo.FormaPagamento;
import com.br.personaladm.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    /**
     * lista formas pagamento ordenado pelo nome
     * @return List<FormaPagamento>
     */
    public List<FormaPagamento> findAll(){
        return formaPagamentoRepository.findAll(Sort.by("nome"));
    }
    /**
     * retorna lista de formas pagamento pelo pelo nome
     * @param nome
     * @return List<FormaPagamento>
     */
    public List<FormaPagamento> findByNome(String nome){
        return formaPagamentoRepository.findFormaPagamentoByNomeContainingIgnoreCaseOrderByNome(nome);
    }
    /**
     * busca formas pagamento pelo id
     * @param id
     * @return FormaPagamento
     */
    public FormaPagamento findById(Long id){
        return formaPagamentoRepository.findById(id).orElseThrow(FormaPagamentoNotFound::new);
    }
    /**
     * atuliza o forma pagamento
     * @param formaPagamento
     * @return FormaPagamento
     */
    public FormaPagamento update(FormaPagamento formaPagamento){
        findById(formaPagamento.getId());
        return formaPagamentoRepository.save(formaPagamento);
    }

    /**
     * salva forma pagamento
     * @param formaPagamento
     * @return FormaPagamento
     */
    public FormaPagamento save(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }
}
