package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.repository.EquipamentoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipamentoServiceImplTest {


    @InjectMocks
    private EquipamentoServiceImpl equipamentoService;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findAll() {

        List<Equipamento> equipamentos = Arrays.asList(new Equipamento(), new Equipamento());
        when(equipamentoRepository.findAll()).thenReturn(equipamentos);

        List<Equipamento> result = equipamentoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(equipamentoRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        String id = "1";
        Equipamento equipamento = new Equipamento();
        equipamento.setId(id);

        when(equipamentoRepository.findById(id)).thenReturn(Optional.of(equipamento));
        Optional<Equipamento> result = equipamentoService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(equipamentoRepository, times(1)).findById(id);
    }

    @Test
    void create() {
        Equipamento equipamento = new Equipamento();
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);

        Equipamento result = equipamentoService.create(equipamento);

        assertNotNull(result);
        verify(equipamentoRepository, times(1)).save(equipamento);
    }

    @Test
    void update() {
        String id = "1";
        Equipamento equipamento = new Equipamento();
        equipamento.setId(id);

        when(equipamentoRepository.findById(id)).thenReturn(Optional.of(equipamento));
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);


        Equipamento result = equipamentoService.update(id, equipamento);


        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(equipamentoRepository, times(1)).findById(id);
        verify(equipamentoRepository, times(1)).save(equipamento);
    }

    @Test
    void delete() {
        String id = "1";
        Equipamento equipamento = new Equipamento();
        equipamento.setId(id);

        when(equipamentoRepository.findById(id)).thenReturn(Optional.of(equipamento));


        doNothing().when(equipamentoRepository).deleteById("1");

        equipamentoService.delete("1");

        verify(equipamentoRepository, times(1)).deleteById("1");
    }

    @Test
    void findByIds() {
        String[] ids = {"1", "2"};
        List<Equipamento> equipamentos = Arrays.asList(new Equipamento(), new Equipamento());

        when(equipamentoRepository.findAllById(Arrays.asList(ids))).thenReturn(equipamentos);

        Set<Equipamento> result = equipamentoService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentoRepository, times(1)).findAllById(Arrays.asList(ids));
    }


    @Test
    void findByNome() {
        String nome = "Projetor";
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(nome);

        List<Equipamento> equipamentos = Arrays.asList(equipamento);

        when(equipamentoRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(equipamentos);

        List<Equipamento> result = equipamentoService.findByNome(nome);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(nome, result.get(0).getNome());
        verify(equipamentoRepository, times(1)).findByNomeContainingIgnoreCase(nome);
    }

    @Test
    void countEquipamentos() {
        when(equipamentoRepository.count()).thenReturn(5L);
        long count = equipamentoService.countEquipamentos();

        verify(equipamentoRepository, times(1)).count();
        assertEquals(5L, count);
    }
}