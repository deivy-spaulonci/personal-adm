package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.ContaNotFoundException;
import com.br.personaladm.business.filter.ContaFilter;
import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    /**
     * salva nova conta
     * @param Conta
     * @return Conta
     */
    public Conta salvarConta(Conta conta){
        return Optional.ofNullable(contaRepository.save(conta))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar a conta!"));
    }

    /**
     * busca conta por id
     * @param id
     * @return Optional<Conta>
     */
    public Optional<Conta> contaPorId(Long id){
        return Optional.ofNullable(contaRepository.findById(id))
                .orElseThrow(()-> new ContaNotFoundException());
    }

    /**
     * apaga conta por id
     * @param id
     */
    public void deletContaPorId(Long id){
        Optional<Conta> conta = contaPorId(id);
        contaRepository.deleteById(id);
    }

    public List<Conta> findAllContas(ContaFilter contaFilter){
        return null;
    }



}
