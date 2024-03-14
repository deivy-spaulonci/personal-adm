package com.br.personaladm.business.service;

import com.br.personaladm.business.exception.ContaNotFoundException;
import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.model.FormaPagamento;
import com.br.personaladm.domain.model.TipoConta;
import com.br.personaladm.domain.repository.ContaRepository;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.NaturalId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Log4j2
@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @MockBean
    ContaRepository contaRepository;

    @MockBean
    ContaService contaService;

    @InjectMocks
    ContaService contaService2;

    Conta conta;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        Long idConta = new AtomicLong().incrementAndGet();
        TipoConta tipoConta = TipoConta.builder()
                .id(idConta)
                .contaCartao(true)
                .nome("tipo conta teste")
                .ativa(Boolean.TRUE)
                .build();
        conta = Conta.builder()
                .id(idConta)
                .codigoBarra("00000000000000000000000000000000000000000000000")
                .vencimento(LocalDate.now().plusDays(10L))
                .emissao(LocalDate.now().minusDays(10L))
                .valor(BigDecimal.TEN)
                .tipoConta(tipoConta)
                .build();
    }

//    @DisplayName("When Create a Person with null e-Mail Should throw Exception")
//    @Test
//    void testCreatePerson_WhithNullEMail_ShouldThrowIllegalArgumentException() {
//        // Given / Arrange
//        person.setEmail(null);
//
//        var expectedMessage = "The Person e-Mail is null or empty!";
//
//        // When / Act & Then / Assert
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> service.createPerson(person),
//                () -> "Empty e-Mail should have cause an IllegalArgumentException!"
//        );
//
//        // Then / Assert
//        assertEquals(
//                expectedMessage,
//                exception.getMessage(),
//                () -> "Exception error message is incorrect!");
//    }

    //@Test
//void test(){
//        fail("teste falhow!!!");
//}



    @Test
    @DisplayName("Teste de busca de contas com filtros com sucesso")
    void testFind_ContaPorFiltros_Sucesso(){
        //var lista = geraListaContas();
        Pageable pageable = PageRequest.of(0, 2);
//        Page<Conta> page = new PageImpl<>(Collections.emptyList());
        //when(contaService.findAllContas(null, pageable)).thenReturn(page);

        Page<Conta> result = contaService2.findAllContas(null, pageable);

//        assertEquals(result.getTotalElements(), page.getTotalElements());

//        Page<Conta> mockContas = new PageImpl<>(lista);
//        Pageable pageable = PageRequest.of(0,2);
//
//        when(contaRepository.findAll(pageable)).thenReturn(mockContas);
//        Page<Conta> result = contaService.findAllContas(null, pageable);

        // Then
//        verify(contaService).findAllContas(null, pageable);
        assertEquals(2, result.getContent().size());
//        assertTrue(result.getContent().contains(new Food("Apple", true)));
//        assertTrue(result.getContent().contains(new Food("Banana", true)));
    }

    @Test
    @DisplayName("Teste setando conta como paga com sucesso.")
    public void testWhen_UpdateSet_ContaPaga_Sucesso(){
        //BDD style
        //given
        //when
        //then

        //given
        Long idForma = new AtomicLong().incrementAndGet();
        var nomeForma = "forma pagamento teste";
        var nomeCompr = "nomecomprovantepdf.pdf";
        var nomeTitul = "nometituloempdf.pdf";
        var obs = "Conta ja paga";

        FormaPagamento formaPagamento = FormaPagamento.builder()
                .nome(nomeForma)
                .id(idForma)
                .build();
        conta.setFormaPagamento(formaPagamento);
        conta.setComprovante(nomeCompr);
        conta.setTitulo(nomeTitul);
        conta.setDataPagamento(LocalDate.now());
        conta.setObs(obs);

        //when
        when(contaService.findContaPorId(conta.getId())).thenReturn(Optional.of(conta));
        when(contaService.salvarConta(any(Conta.class))).thenAnswer(invocation -> invocation.getArguments());

        var contaAux = contaService.findContaPorId(conta.getId()).get();
        var msg = "Teste de alteração de conta paga com sucesso - %s diferente!";
        //()->(empressao lambad) so vai ocorrer se o teste falhar

        //then
        assertEquals(contaAux.getFormaPagamento(), formaPagamento, ()-> msg.format(msg, "forma de pagamento"));
        assertEquals(contaAux.getComprovante(), nomeCompr, ()-> msg.format(msg, "nome comprovante"));
        assertEquals(contaAux.getTitulo(), nomeTitul,()-> msg.format(msg, "nome titulo"));
        assertEquals(contaAux.getDataPagamento(), LocalDate.now(), ()-> msg.format(msg, "data pagamento"));
        assertEquals(contaAux.getObs(), obs, ()-> msg.format(msg, "obs"));
    }

    @Test
    @DisplayName("Teste busca de conta por id com sucesso")
    void testWhen_Find_ContaPorId_Successo(){
        when(contaService.findContaPorId(conta.getId())).thenReturn(Optional.ofNullable(conta));
        var contaAux = contaService.findContaPorId(conta.getId());
        String msg = "Teste de busca de conta por id - %s diferente!";
        assertEquals(conta.getCodigoBarra(), contaAux.get().getCodigoBarra(), ()-> msg.format("codigo de barras"));
        assertEquals(conta.getTipoConta(), contaAux.get().getTipoConta(), ()-> msg.format(msg, "tipo conta"));
        assertEquals(conta.getEmissao(), contaAux.get().getEmissao(), ()-> msg.format(msg, "emissão"));
        assertEquals(conta.getVencimento(), contaAux.get().getVencimento(), ()-> msg.format(msg, "vencimento"));
        assertEquals(conta.getObs(), contaAux.get().getObs(), ()-> msg.format(msg, "obs"));
        assertEquals(conta.getValor(), contaAux.get().getValor(), ()-> msg.format(msg, "valor"));

        verify(contaService, times(1)).findContaPorId(conta.getId());
    }

    //@Test(expected=RuntimeException.class)
    @Test
    @DisplayName("Teste busca de conta pelo id retornando uma Excepeption")
    void testWhen_Find_ContaPorId_ReturnExeception(){
        Long id = new AtomicLong().incrementAndGet();
        when(contaService.findContaPorId(id)).thenThrow(new ContaNotFoundException());
        assertThrows(ContaNotFoundException.class, () -> contaService.findContaPorId(id));
    }

    @Test
    @DisplayName("Teste salvar conta em aberto com sucesso")
    void testSave_ContaEmAberto_Sucesso(){
        when(contaService.salvarConta(conta)).thenReturn(conta);
    }

    @Test
    @DisplayName("Teste de exclusão de conta pelo id com sucesso")
    void testDelete_ContaPorId_Sucesso(){
        contaService.deleteContaPorId(conta.getId());
        verify(contaService).deleteContaPorId(conta.getId());
    }

    ArrayList<Conta> geraListaContas(){
        ArrayList<Conta> lista = new ArrayList<>();

        Long IDTIPO = new AtomicLong().incrementAndGet();
        var NOMETIPO = "Tipo Conta Teste";
        var tipoContaA = TipoConta.builder().id(IDTIPO+1).contaCartao(true).nome(NOMETIPO+ " A").ativa(Boolean.TRUE).build();
        var tipoContaB = TipoConta.builder().id(IDTIPO+2).contaCartao(true).nome(NOMETIPO+ " B").ativa(Boolean.FALSE).build();
        var tipoContaC = TipoConta.builder().id(IDTIPO+3).contaCartao(true).nome(NOMETIPO+ " C").ativa(Boolean.FALSE).build();

        Long IDFORMA = new AtomicLong().incrementAndGet();
        var NOMEfORMA = "Forma Pagamento Teste";
        var formaPagtoA = FormaPagamento.builder().id(IDFORMA).nome(NOMETIPO+ " A").build();
        var formaPagtoB = FormaPagamento.builder().id(IDFORMA).nome(NOMETIPO+ " B").build();
        var formaPagtoC = FormaPagamento.builder().id(IDFORMA).nome(NOMETIPO+ " C").build();

        Long IDCONTA = new AtomicLong().incrementAndGet();
        var codBar = "0000000000000000000000000000000000000000000000";
        Conta contaA = Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"1")
                .vencimento(LocalDate.now().plusDays(10L))
                .emissao(LocalDate.now().minusDays(10L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaA).build();
        lista.add(contaA);

        var contaB = Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"2")
                .vencimento(LocalDate.now().plusDays(5L))
                .emissao(LocalDate.now().minusDays(5L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaB).build();
        lista.add(contaB);

        var contaC = Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"3")
                .vencimento(LocalDate.now().plusDays(7L))
                .emissao(LocalDate.now().minusDays(7L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaC).build();
        lista.add(contaC);

        var nomeCompr = "nomecomprovantepdf.pdf";
        var nomeTitul = "nometituloempdf.pdf";

        var contaAPaga = com.br.personaladm.domain.model.Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"4")
                .vencimento(LocalDate.now().plusDays(7L))
                .formaPagamento(formaPagtoA)
                .dataPagamento(LocalDate.now().plusDays(7L))
                .comprovante(nomeCompr)
                .titulo(nomeTitul)
                .emissao(LocalDate.now().minusDays(7L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaC).build();
        lista.add(contaAPaga);

        var contaBPaga = com.br.personaladm.domain.model.Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"4")
                .vencimento(LocalDate.now().plusDays(7L))
                .formaPagamento(formaPagtoC)
                .dataPagamento(LocalDate.now().plusDays(7L))
                .comprovante(nomeCompr)
                .titulo(nomeTitul)
                .emissao(LocalDate.now().minusDays(7L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaC).build();
        lista.add(contaBPaga);

        var contaCPaga = com.br.personaladm.domain.model.Conta.builder().id(IDCONTA)
                .codigoBarra(codBar+"5")
                .vencimento(LocalDate.now().plusDays(7L))
                .formaPagamento(formaPagtoB)
                .dataPagamento(LocalDate.now().plusDays(7L))
                .comprovante(nomeCompr)
                .titulo(nomeTitul)
                .emissao(LocalDate.now().minusDays(7L))
                .valor(new BigDecimal(Math.random()))
                .tipoConta(tipoContaC).build();
        lista.add(contaCPaga);

        return lista;
    }

}
