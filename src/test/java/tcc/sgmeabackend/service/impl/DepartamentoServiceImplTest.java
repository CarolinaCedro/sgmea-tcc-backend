package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.repository.DepartamentoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartamentoServiceImplTest {

    @InjectMocks
    private DepartamentoServiceImpl departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        // Mocking the data returned by the repository
        List<Departamento> departamentos = Arrays.asList(new Departamento(), new Departamento());
        when(departamentoRepository.findAll()).thenReturn(departamentos);

        // Calling the service method
        List<Departamento> result = departamentoService.findAll();

        // Verifications and assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departamentoRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        String id = "1";
        Departamento departamento = new Departamento();
        departamento.setId(id);

        when(departamentoRepository.findById(id)).thenReturn(Optional.of(departamento));

        // Call the service method
        Optional<Departamento> result = departamentoService.findById(id);

        // Verify and assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(departamentoRepository, times(1)).findById(id);
    }

    @Test
    void create() {
        Departamento departamento = new Departamento();
        when(departamentoRepository.save(departamento)).thenReturn(departamento);

        // Call the service method
        Departamento result = departamentoService.create(departamento);

        // Verify and assert
        assertNotNull(result);
        verify(departamentoRepository, times(1)).save(departamento);
    }

    @Test
    void update() {
        String id = "1";
        Departamento departamento = new Departamento();
        departamento.setId(id);

        when(departamentoRepository.findById(id)).thenReturn(Optional.of(departamento));
        when(departamentoRepository.save(departamento)).thenReturn(departamento);

        // Call the service method
        Departamento result = departamentoService.update(id, departamento);

        // Verify and assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(departamentoRepository, times(1)).findById(id);
        verify(departamentoRepository, times(1)).save(departamento);
    }

    @Test
    void delete() {

        String id = "1";
        Departamento departamento = new Departamento();
        departamento.setId(id);

        when(departamentoRepository.findById(id)).thenReturn(Optional.of(departamento));


        doNothing().when(departamentoRepository).deleteById("1");

        // Call the service method
        departamentoService.delete("1");

        // Verify
        verify(departamentoRepository, times(1)).deleteById("1");
    }

    @Test
    void findByIds() {
        String[] ids = {"1", "2"};
        List<Departamento> departamentos = Arrays.asList(new Departamento(), new Departamento());

        // Converta o array de ids para uma lista antes de chamar o método findAllById
        when(departamentoRepository.findAllById(Arrays.asList(ids))).thenReturn(departamentos);

        // Chama o método do serviço
        Set<Departamento> result = departamentoService.findByIds(ids);

        // Verifica e faz assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departamentoRepository, times(1)).findAllById(Arrays.asList(ids));
    }


@Test
void findByNome() {
    String nome = "Departamento de TI";
    Departamento departamento = new Departamento();
    departamento.setNome(nome);

    // Cria uma lista com o departamento
    List<Departamento> departamentos = Arrays.asList(departamento);

    // Simula o retorno da lista de departamentos
    when(departamentoRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(departamentos);

    // Chama o método do serviço
    List<Departamento> result = departamentoService.findByNome(nome);

    // Verifica e faz o assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(nome, result.get(0).getNome());
    verify(departamentoRepository, times(1)).findByNomeContainingIgnoreCase(nome);
}

}
