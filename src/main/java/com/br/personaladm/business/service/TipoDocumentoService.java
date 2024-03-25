package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.TipoDocumentoNotFound;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import com.br.personaladm.domain.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    /**
     * lista tipo de documentos ordenado pelo nome
     * @return List<TipoDocumento>
     */
    public List<TipoDocumento> findAll(){
        return tipoDocumentoRepository.findAll(Sort.by("nome"));
    }
    /**
     * retorna lista de tipo de documentos pelo pelo nome
     * @param nome
     * @return List<TipoDocumento>
     */
    public List<TipoDocumento> findByNome(String nome){
        return tipoDocumentoRepository.findTipoContaByNomeContainsIgnoreCaseOrderByNome(nome);
    }
    /**
     * busca tipo de documentos pelo id
     * @param id
     * @return TipoDocumento
     */
    public TipoDocumento findById(Long id){
        return tipoDocumentoRepository.findById(id).orElseThrow(TipoDocumentoNotFound::new);
    }
    /**
     * atuliza o tipo documento
     * @param tipoDocumento
     * @return TipoDocumento
     */
    public TipoDocumento update(TipoDocumento tipoDocumento){
        findById(tipoDocumento.getId());
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    /**
     * salva tipo de documento
     * @param tipoDocumento
     * @return TipoDocumento
     */
    public TipoDocumento save(TipoDocumento tipoDocumento){
        return tipoDocumentoRepository.save(tipoDocumento);
    }
}
