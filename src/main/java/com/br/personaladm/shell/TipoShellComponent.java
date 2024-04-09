package com.br.personaladm.shell;

import com.br.personaladm.domain.model.tipo.FormaPagamento;
import com.br.personaladm.domain.model.tipo.TipoDespesa;
import com.br.personaladm.domain.model.tipo.TipoDocumento;
import com.br.personaladm.domain.repository.generic.GenericReporitoryTipo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.*;

@Log4j2
@ShellComponent
public class TipoShellComponent extends AbstractShellComponent {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    GenericReporitoryTipo<TipoDespesa> tipoDespesaGenericReporitoryTipo;
    @Autowired
    GenericReporitoryTipo<FormaPagamento> formaPagamentoGenericReporitoryTipo;
    @Autowired
    GenericReporitoryTipo<TipoDocumento> tipoDocumentoGenericReporitoryTipo;
    @ShellMethod("mostra os tipos de despesa")
    public void tipoDespesa() {
        List<TipoDespesa> tipos = tipoDespesaGenericReporitoryTipo.findAll(Sort.by("nome"));
        tipos.forEach(tipoDespesa -> System.out.println(String.format("% 5d", tipoDespesa.getId()) + " - " + tipoDespesa.getNome()));
    }

    @ShellMethod("mostra as formas de pagamento")
    public void formaPagamento() {
        List<FormaPagamento> tipos = formaPagamentoGenericReporitoryTipo.findAll(Sort.by("nome"));
        tipos.forEach(formaPagamento -> System.out.println(String.format("% 5d", formaPagamento.getId())+" - " +formaPagamento.getNome()));
    }
    @ShellMethod("mostra os tipos de documentos")
    public void tipoDocumento() {
        List<TipoDocumento> tipos = tipoDocumentoGenericReporitoryTipo.findAll(Sort.by("nome"));
        tipos.forEach(tipoDocumento -> System.out.println(String.format("% 5d", tipoDocumento.getId())+" - " +tipoDocumento.getNome()));
    }
}
