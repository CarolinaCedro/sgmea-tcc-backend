package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.repository.TecnicoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TecnicoServiceImplTest {


    @InjectMocks
    private TecnicoServiceImpl tecnicoService;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {

        List<Tecnico> tecnicos = Arrays.asList(new Tecnico(), new Tecnico());
        when(tecnicoRepository.findAll()).thenReturn(tecnicos);

        List<Tecnico> result = tecnicoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tecnicoRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        String id = "1";
        Tecnico tecnico = new Tecnico();
        tecnico.setId(id);

        when(tecnicoRepository.findById(id)).thenReturn(Optional.of(tecnico));

        Optional<Tecnico> result = tecnicoService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(tecnicoRepository, times(1)).findById(id);
    }

    @Test
    void create() {

        Tecnico tecnico = new Tecnico();
        when(tecnicoRepository.save(tecnico)).thenReturn(tecnico);

        Tecnico result = tecnicoService.create(tecnico);
        assertNotNull(result);
        verify(tecnicoRepository, times(1)).save(tecnico);
    }

    @Test
    void update() {
        String id = "1";
        Tecnico tecnico = new Tecnico();
        tecnico.setId(id);

        when(tecnicoRepository.findById(id)).thenReturn(Optional.of(tecnico));
        when(tecnicoRepository.save(tecnico)).thenReturn(tecnico);

        Tecnico result = tecnicoService.update(id, tecnico);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(tecnicoRepository, times(1)).findById(id);
        verify(tecnicoRepository, times(1)).save(tecnico);
    }

    @Test
    void delete() {
        String id = "1";
        Tecnico tecnico = new Tecnico();
        tecnico.setId(id);

        when(tecnicoRepository.findById(id)).thenReturn(Optional.of(tecnico));


        doNothing().when(tecnicoRepository).deleteById("1");

        tecnicoService.delete("1");

        verify(tecnicoRepository, times(1)).deleteById("1");

    }

    @Test
    void findByIds() {
        String[] ids = {"1", "2"};
        List<Tecnico> funcionarios = Arrays.asList(new Tecnico(), new Tecnico());

        when(tecnicoRepository.findAllById(Arrays.asList(ids))).thenReturn(funcionarios);

        Set<Tecnico> result = tecnicoService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tecnicoRepository, times(1)).findAllById(Arrays.asList(ids));
    }

    @Test
    void findByNome() {
        String nome = "Ana Carolina";
        Tecnico tecnico = new Tecnico();
        tecnico.setNome(nome);

        List<Tecnico> tecnicos = Arrays.asList(tecnico);
        when(tecnicoRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(tecnicos);

        List<Tecnico> result = tecnicoService.findByNome(nome);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(nome, result.get(0).getNome());
        verify(tecnicoRepository, times(1)).findByNomeContainingIgnoreCase(nome);
    }
}