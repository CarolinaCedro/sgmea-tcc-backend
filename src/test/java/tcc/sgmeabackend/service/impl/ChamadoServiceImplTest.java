package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.*;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.repository.ChamadoCriadoRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChamadoServiceImplTest {


    @InjectMocks
    private ChamadoServiceImpl chamadoService;

    @Mock
    private EmailService emailService;

    @Mock
    private ChamadoCriadoRepository chamadoCriadoRepository;

    @Mock
    private TecnicoRepository tecnicoRepository;
    @Mock
    private GestorRepository gestorRepository;
    @Mock
    private ChamadoAtribuidoRepository chamadoAtribuidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {


        List<ChamadoCriado> chamado = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findAll()).thenReturn(chamado);

        List<ChamadoCriado> result = chamadoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chamadoCriadoRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        String id = "1";
        ChamadoCriado chamado = new ChamadoCriado();
        chamado.setId(id);

        when(chamadoCriadoRepository.findById(id)).thenReturn(Optional.of(chamado));
        Optional<ChamadoCriado> result = chamadoService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(chamadoCriadoRepository, times(1)).findById(id);
    }

    @Test
    void create() {

        ChamadoCriado chamado = new ChamadoCriado();
        when(chamadoCriadoRepository.save(chamado)).thenReturn(chamado);

        ChamadoCriado result = chamadoService.create(chamado);

        assertNotNull(result);
        verify(chamadoCriadoRepository, times(1)).save(chamado);
    }

    @Test
    void update() {

        String id = "1";
        ChamadoCriado chamado = new ChamadoCriado();
        chamado.setId(id);

        when(chamadoCriadoRepository.findById(id)).thenReturn(Optional.of(chamado));
        when(chamadoCriadoRepository.save(chamado)).thenReturn(chamado);


        ChamadoCriado result = chamadoService.update(id, chamado);


        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(chamadoCriadoRepository, times(1)).findById(id);
        verify(chamadoCriadoRepository, times(1)).save(chamado);

    }

    @Test
    void delete() {
        String id = "1";
        ChamadoCriado chamado = new ChamadoCriado();
        chamado.setId(id);

        when(chamadoCriadoRepository.findById(id)).thenReturn(Optional.of(chamado));


        doNothing().when(chamadoCriadoRepository).deleteById("1");

        chamadoService.delete("1");

        verify(chamadoCriadoRepository, times(1)).deleteById("1");

    }

    @Test
    void findByIds() {
        String[] ids = {"1", "2"};
        List<ChamadoCriado> chamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());

        when(chamadoCriadoRepository.findAllById(Arrays.asList(ids))).thenReturn(chamados);

        Set<ChamadoCriado> result = chamadoService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chamadoCriadoRepository, times(1)).findAllById(Arrays.asList(ids));
    }


    @Test
    void getChamadosEncerrados() {

        ChamadoCriado chamado1 = new ChamadoCriado();
        chamado1.setStatus(Status.CONCLUIDO);

        ChamadoCriado chamado2 = new ChamadoCriado();
        chamado2.setStatus(Status.ENCERRADO);

        ChamadoCriado chamado3 = new ChamadoCriado();
        chamado3.setStatus(Status.ABERTO);


        List<ChamadoCriado> chamados = Arrays.asList(chamado1, chamado2, chamado3);
        when(chamadoCriadoRepository.findByStatusIn(anyList())).thenReturn(Arrays.asList(chamado1, chamado2));


        List<ChamadoCriado> chamadosEncerrados = chamadoService.getChamadosEncerrados();


        System.out.println("Chamados encerrados: " + chamadosEncerrados);


        assertFalse(chamadosEncerrados.isEmpty(), "A lista de chamados encerrados não deveria estar vazia.");
        assertEquals(2, chamadosEncerrados.size(), "Deveria haver dois chamados encerrados.");
        assertEquals(Status.CONCLUIDO, chamadosEncerrados.get(0).getStatus(), "O status do primeiro chamado encerrado deveria ser CONCLUIDO.");
        assertEquals(Status.ENCERRADO, chamadosEncerrados.get(1).getStatus(), "O status do segundo chamado encerrado deveria ser ENCERRADO.");
    }


    @Test
    void atribuirChamado() {

        ChamadoCriado chamadoCriado = new ChamadoCriado();
        chamadoCriado.setId("1");
        chamadoCriado.setFuncionario(new Funcionario());
        chamadoCriado.getFuncionario().setEmail("funcionario@example.com");
        chamadoCriado.setStatus(Status.ANDAMENTO);


        Tecnico tecnico = new Tecnico();
        tecnico.setId("2");

        Gestor gestor = new Gestor();
        gestor.setId("3");


        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoAtribuido.setChamadoCriado(chamadoCriado);
        chamadoAtribuido.setTecnico(tecnico);
        chamadoAtribuido.setGestor(gestor);
        chamadoAtribuido.setPrioridade(Prioridade.ALTA);


        when(chamadoCriadoRepository.findById(any())).thenReturn(Optional.of(chamadoCriado));


        when(tecnicoRepository.findById(any())).thenReturn(Optional.of(tecnico));


        when(gestorRepository.findById(any())).thenReturn(Optional.of(gestor));


        when(chamadoAtribuidoRepository.save(any())).thenReturn(chamadoAtribuido);


        ChamadoAtribuido result = chamadoService.atribuirChamado(chamadoAtribuido);


        assertNotNull(result, "O resultado não deve ser nulo.");
        assertEquals(chamadoAtribuido.getChamadoCriado().getId(), result.getChamadoCriado().getId(), "O ID do chamado atribuído deve ser igual ao resultado.");


        verify(emailService, times(1)).chamadoAtribuido(
                eq(chamadoCriado.getFuncionario().getEmail()),
                eq(chamadoAtribuido.getGestor().getEmail()),
                any(ChamadoAtribuido.class)
        );
    }


    @Test
    void consolidarChamado() {

        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoAtribuido.setId("1");
        chamadoAtribuido.setGestor(new Gestor());
        chamadoAtribuido.setChamadoCriado(new ChamadoCriado());
        chamadoAtribuido.getChamadoCriado().setId("1");

        // Criação de um chamado criado
        ChamadoCriado chamadoCriado = new ChamadoCriado();
        chamadoCriado.setId("1");
        chamadoCriado.setStatus(Status.ANDAMENTO);
        chamadoCriado.setFuncionario(new Funcionario());
        chamadoCriado.getFuncionario().setEmail("funcionario@example.com");


        when(chamadoAtribuidoRepository.findById("1")).thenReturn(Optional.of(chamadoAtribuido));


        when(chamadoCriadoRepository.findById("1")).thenReturn(Optional.of(chamadoCriado));


        String observacaoConsolidacao = "Observação de teste";
        ChamadoConsolidado result = chamadoService.consolidarChamado("1", observacaoConsolidacao);


        assertNotNull(result, "O resultado não deve ser nulo.");
        assertEquals("1", result.getId(), "O ID do chamado consolidado deve ser igual ao ID do chamado criado.");
        assertEquals(Status.CONCLUIDO, chamadoCriado.getStatus(), "O status do chamado criado deve ser atualizado para CONCLUIDO.");
        assertEquals(observacaoConsolidacao, chamadoCriado.getObservacaoConsolidacao(), "A observação de consolidação deve ser corretamente atribuída.");

    }


    @Test
    void getChamadoChartData() {

        when(chamadoCriadoRepository.countChamadosByWeek(any(), any(), eq(Status.CONCLUIDO))).thenReturn(5);
        when(chamadoCriadoRepository.findTopEquipamentosByChamados(eq(Status.CONCLUIDO), any())).thenReturn(List.of(
                Map.of("equipamento", "Equipamento A"),
                Map.of("equipamento", "Equipamento B"),
                Map.of("equipamento", "Equipamento C"),
                Map.of("equipamento", "Equipamento D")
        ));


        Map<String, Object> chartData = chamadoService.getChamadoChartData();


        assertEquals(5, ((int[]) chartData.get("thisWeek"))[0]);
        assertEquals(5, ((int[]) chartData.get("lastWeek"))[0]);
        assertEquals(4, ((Object[]) chartData.get("labels")).length);
    }

    @Test
    void findAllByStatusNot() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findAllByStatusNotAndAlocadoIsFalse(Status.CONCLUIDO)).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.findAllByStatusNot(Status.CONCLUIDO);


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findAllByStatusNotAndAlocadoIsFalse(Status.CONCLUIDO);
    }

    @Test
    void findAllByStatusNotAndAlocadoFalse() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findAllByStatusNotInAndAlocadoFalse(anyList())).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.findAllByStatusNotAndAlocadoFalse(Arrays.asList(Status.ANDAMENTO));


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findAllByStatusNotInAndAlocadoFalse(anyList());
    }

    @Test
    void findAllChamadosAlocados() {

        List<ChamadoAtribuido> mockedChamados = Arrays.asList(new ChamadoAtribuido(), new ChamadoAtribuido());
        when(chamadoAtribuidoRepository.findAllChamadosAlocadosSemConcluidos()).thenReturn(mockedChamados);


        List<ChamadoAtribuido> result = chamadoService.findAllChamadosAlocados();

        assertEquals(2, result.size());
        verify(chamadoAtribuidoRepository).findAllChamadosAlocadosSemConcluidos();
    }

    @Test
    void getChamadosConcluidos() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findByStatus(Status.CONCLUIDO)).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.getChamadosConcluidos();


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findByStatus(Status.CONCLUIDO);
    }

    @Test
    void findByTituloContainingAndStatusIn() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findByTituloContainingAndStatusIn(anyString(), anyList())).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.findByTituloContainingAndStatusIn("Título", Arrays.asList(Status.CONCLUIDO));


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findByTituloContainingAndStatusIn(anyString(), anyList());
    }

    @Test
    void findByTitulo() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findByTituloContainingIgnoreCase(anyString())).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.findByTitulo("Título");


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findByTituloContainingIgnoreCase(anyString());
    }

    @Test
    void findAllByTituloContainingAndStatusNotInAndAlocadoFalse() {

        List<ChamadoCriado> mockedChamados = Arrays.asList(new ChamadoCriado(), new ChamadoCriado());
        when(chamadoCriadoRepository.findAllByTituloContainingAndStatusNotInAndAlocadoFalse(anyString(), anyList())).thenReturn(mockedChamados);


        List<ChamadoCriado> result = chamadoService.findAllByTituloContainingAndStatusNotInAndAlocadoFalse("Título", Arrays.asList(Status.CONCLUIDO));


        assertEquals(2, result.size());
        verify(chamadoCriadoRepository).findAllByTituloContainingAndStatusNotInAndAlocadoFalse(anyString(), anyList());
    }
}
