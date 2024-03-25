package com.br.personaladm.api.controller;

import com.br.personaladm.api.mapper.ContaMapper;
import com.br.personaladm.api.mapper.TipoContaMapper;
import com.br.personaladm.business.service.ContaService;
import com.br.personaladm.business.service.TipoContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContaController {
    @Autowired
    private ContaService contaService;
    @Autowired
    private TipoContaService tipoContaService;
    private static final ContaMapper contaMapper = ContaMapper.INSTANCE;
    private static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;
    @GetMapping("/conta")
    public String get(Model model) {
        model.addAttribute("contas", contaMapper.toDtoList(contaService.findAll()));
//        model.addAttribute("tiposConta", tipoContaMapper.toDtoList(tipoContaService.findAllTipos()));
        return "conta";
    }
}
