package com.br.personaladm.business.service;

import com.br.personaladm.domain.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class service para documento
 */
@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;
}
