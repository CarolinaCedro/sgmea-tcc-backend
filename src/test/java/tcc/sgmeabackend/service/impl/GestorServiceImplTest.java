package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.repository.GestorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorServiceImplTest {


    @InjectMocks
    private GestorServiceImpl gestorService;

    @Mock
    private GestorRepository gestorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findAll() {
        List<Gestor> gestores = Arrays.asList(new Gestor(), new Gestor());
        when(gestorRepository.findAll()).thenReturn(gestores);

        List<Gestor> result = gestorService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gestorRepository, times(1)).findAll();
    }

    @Test
    void findById() {


        String id = "1";
        Gestor gestor = new Gestor();
        gestor.setId(id);

        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));

        Optional<Gestor> result = gestorService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(gestorRepository, times(1)).findById(id);

    }

    @Test
    void create() {
        Gestor gestor = new Gestor();
        when(gestorRepository.save(gestor)).thenReturn(gestor);

        Gestor result = gestorService.create(gestor);
        assertNotNull(result);
        verify(gestorRepository, times(1)).save(gestor);
    }

    @Test
    void update() {
        String id = "1";
        Gestor gestor = new Gestor();
        gestor.setId(id);

        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));
        when(gestorRepository.save(gestor)).thenReturn(gestor);

        // Call the service method
        Gestor result = gestorService.update(id, gestor);

        // Verify and assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(gestorRepository, times(1)).findById(id);
        verify(gestorRepository, times(1)).save(gestor);
    }

    @Test
    void delete() {
        String id = "1";
        Gestor gestor = new Gestor();
        gestor.setId(id);

        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));


        doNothing().when(gestorRepository).deleteById("1");

        gestorService.delete("1");

        verify(gestorRepository, times(1)).deleteById("1");
    }

    @Test
    void findByIds() {
        String[] ids = {"1", "2"};
        List<Gestor> gestores = Arrays.asList(new Gestor(), new Gestor());

        when(gestorRepository.findAllById(Arrays.asList(ids))).thenReturn(gestores);

        Set<Gestor> result = gestorService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(gestorRepository, times(1)).findAllById(Arrays.asList(ids));
    }


    @Test
    void findByNome() {

        String nome = "Ana Carolina";
        Gestor gestor = new Gestor();
        gestor.setNome(nome);

        List<Gestor> gestores = Arrays.asList(gestor);
        when(gestorRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(gestores);

        List<Gestor> result = gestorService.findByNome(nome);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(nome, result.get(0).getNome());
        verify(gestorRepository, times(1)).findByNomeContainingIgnoreCase(nome);
    }
}