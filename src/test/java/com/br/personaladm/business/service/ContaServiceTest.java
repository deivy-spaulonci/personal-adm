package com.br.personaladm.business.service;

import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.model.TipoConta;
import com.br.personaladm.domain.repository.ContaRepository;
import com.br.personaladm.domain.repository.TipoContaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Log4j2
@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @MockBean
    ContaRepository contaRepository;

    @MockBean
    TipoContaRepository tipoContaRepository;

    @InjectMocks
    ContaService contaService;

    Conta conta;

    @BeforeEach
    void init(){
        TipoConta tipoConta = TipoConta.builder()
                .id(1L)
                .contaCartao(true)
                .nome("tipo conta teste")
                .ativa(Boolean.TRUE)
                .build();
        conta = Conta.builder()
                .id(1L)
                .codigoBarra("00000000000000000000000000000000000000000000000")
                .vencimento(LocalDate.now().plusDays(10L))
                .emissao(LocalDate.now().minusDays(10L))
                .valor(BigDecimal.TEN)
                .tipoConta(tipoConta)
                .build();
    }

//    @Test
//    public void testSalvarContaPagaSucesso(){
//        FormaPagamento formaPagamento = FormaPagamento.builder()
//                .nome("forma pagamento teste")
//                .id(1L)
//                .build();
//        conta.setFormaPagamento(formaPagamento);
//        conta.setComprovante("nomecomprovantepdf.pdf");
//        conta.setTitulo("nometituloempdf.pdf");
//        conta.setDataPagamento(LocalDate.now());
//        conta.setObs("Conta ja paga");
//
//        when(contaService.salvarConta(conta)).thenReturn(conta);
//    }
    @Test
    public void testSalvarContaEmAbertoSucesso(){
        contaService.salvarConta(conta);
        //when(contaService.salvarConta(conta)).thenReturn(conta);
        //Mockito.when(contaRepository.findAll()).thenReturn(List<conta>);
        //Assert.notEmpty(contaRepository.findAll(), "nao pode star vazia");
        //Assertions.assertTrue(contaRepository.findById(1L).isPresent());
        //contaRepository.findAll().forEach(System.out::println);
    }
}
