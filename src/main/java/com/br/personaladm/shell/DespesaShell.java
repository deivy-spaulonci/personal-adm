package com.br.personaladm.shell;

import com.br.personaladm.api.filter.DespesaFilter;
import com.br.personaladm.business.service.DespesaService;
import com.br.personaladm.domain.model.Despesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Command(command = "parent")
@ShellComponent
public class DespesaShell {
    @Autowired
    DespesaService despesaService;

    @Command(command = "example")
    public void  example(@Option(required = false) Long idTipo) {
//        Page<Despesa> page = despesaService.getPageByFilters(
//                new DespesaFilter(null, null, null, null, null, null),
//                Pageable.ofSize(10));
//
//        page.getContent().forEach(despesa -> {
//            var linha = String.format("% 5d", despesa.getId())+despesa.getTipoDespesa().getNome();
//            System.out.println(linha);
//        });

    }
}
