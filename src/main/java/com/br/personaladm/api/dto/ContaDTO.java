package com.br.personaladm.api.dto;

import com.br.personaladm.domain.model.*;
import com.br.personaladm.domain.model.tipo.FormaPagamento;
import com.br.personaladm.domain.model.tipo.TipoConta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {

    private Long id;

    @NotNull
    private TipoConta tipoConta;

    @NotNull
    private String codigoBarra;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate emissao;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimento;

    private int parcela;
    private int totalParcelas;
    private BigDecimal multa;
    private List<Fatura> faturas;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;

    @NotNull
    private BigDecimal valor;

    private FormaPagamento formaPagamento;
    private Fornecedor fornecedor;
    private String obs;

    @JsonGetter(value = "intStatus")
    @Transient
    public int getIntStatus() {
        if(getDataPagamento()==null && getDataPagamento()==null){
            if(getVencimento().isAfter(LocalDate.now()))
                return 1;
            if(getVencimento().isEqual(LocalDate.now()))
                return 0;
            if(getVencimento().isBefore(LocalDate.now()))
                return -1;
        }
        return 2;

    }

    @JsonGetter(value = "status")
    @Transient
    public String getStatus() {
        return switch (getIntStatus()) {
            case 1 -> ContaStatus.ABERTO.getNome();
            case 0 -> ContaStatus.HOJE.getNome();
            case -1 -> ContaStatus.ATRASADO.getNome();
            case 2 -> ContaStatus.PAGO.getNome();
            default -> ContaStatus.ABERTO.getNome();
        };
    }
}
