package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.model.*;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.service.EmailService;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChamadoControllerTest {

    @InjectMocks
    private ChamadoController chamadoController;

    @Mock
    private ChamadoServiceImpl chamadoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ChamadoAtribuidoRepository chamadoAtribuidoRepository;

    @Mock
    private GestorServiceImpl gestorService;

    @Mock
    private FuncionarioServiceImpl funcionarioService;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtribuirChamado() {

        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoController.atribuirChamado(chamadoAtribuido);
        verify(chamadoService, times(1)).atribuirChamado(chamadoAtribuido);
    }

    @Test
    void testChamadoAtribuidoById() {
        String chamadoId = "1";
        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        when(chamadoAtribuidoRepository.findById(chamadoId)).thenReturn(Optional.of(chamadoAtribuido));
        ResponseEntity<Optional<ChamadoAtribuido>> response = chamadoController.chamadoAtribuidoById(chamadoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(chamadoAtribuido, response.getBody().get());
    }

//    @Test
//    void testChamadoAtribuidos() {
//        List<ChamadoAtribuido> chamados = List.of(new ChamadoAtribuido());
//        when(chamadoService.findAllChamadosAlocados()).thenReturn(chamados);
//
//        ResponseEntity<PageableResource<ChamadoAtribuido>> response = chamadoController.chamadoAtribuidos();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(chamados, response.getBody().getRecords());
//    }

    @Test
    void testChamadosEncerrados() {
        List<ChamadoCriado> chamados = List.of(new ChamadoCriado());
        when(chamadoService.getChamadosEncerrados()).thenReturn(chamados);

        ResponseEntity<PageableResource<ChamadoCriado>> response = chamadoController.chamadosEncerrados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chamados, response.getBody().getRecords());
    }

    @Test
    void testChamadosConcluidos() {
        List<ChamadoCriado> chamados = List.of(new ChamadoCriado());
        when(chamadoService.getChamadosConcluidos()).thenReturn(chamados);
        when(modelMapper.map(any(ChamadoCriado.class), eq(ChamadoReportResponse.class)))
                .thenReturn(new ChamadoReportResponse());

        ResponseEntity<PageableResource<ChamadoReportResponse>> response = chamadoController.chamadosConcluidos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getRecords().size());
    }

    @Test
    void testDelete() {
        String chamadoId = "1";
        ChamadoCriado chamadoCriado = new ChamadoCriado();
        when(chamadoService.findById(chamadoId)).thenReturn(Optional.of(chamadoCriado));


        chamadoController.delete(chamadoId);


        verify(chamadoService, times(1)).create(any(ChamadoCriado.class));
    }

    @Test
    void testConsolidarChamado() {

        String chamadoId = "1";
        String observacao = "Observação de teste";
        ChamadoConsolidado consolidado = new ChamadoConsolidado();
        when(chamadoService.consolidarChamado(chamadoId, observacao)).thenReturn(consolidado);


        ResponseEntity<ChamadoConsolidado> response = chamadoController.consolidarChamado(chamadoId, observacao);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consolidado, response.getBody());
    }


    @Test
    void testCreateChamado() {

        ChamadoCriado chamadoCriado = new ChamadoCriado();


        Funcionario funcionario = new Funcionario();
        funcionario.setId("123");
        funcionario.setNome("Nome do Funcionário");


        Gestor gestor = new Gestor();
        gestor.setId("456");
        funcionario.setGestor(gestor);


        when(funcionarioService.create(any(Funcionario.class))).thenReturn(funcionario);
        when(funcionarioService.findById(anyString())).thenReturn(Optional.of(funcionario));
        when(gestorService.findById(anyString())).thenReturn(Optional.of(gestor));


        Funcionario funcionarioCriado = funcionarioService.create(funcionario);


        chamadoCriado.setFuncionario(funcionarioCriado);


        ResponseEntity<ChamadoCriado> response = chamadoController.create(chamadoCriado);

    }


}
