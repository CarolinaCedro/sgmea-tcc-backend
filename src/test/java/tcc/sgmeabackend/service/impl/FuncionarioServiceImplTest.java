package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.repository.FuncionarioRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceImplTest {


    @InjectMocks
    private FuncionarioServiceImpl funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {

        List<Funcionario> funcionarios = Arrays.asList(new Funcionario(), new Funcionario());
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        List<Funcionario> result = funcionarioService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    void findById() {

        String id = "1";
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));

        Optional<Funcionario> result = funcionarioService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(funcionarioRepository, times(1)).findById(id);
    }

    @Test
    void create() {

        Funcionario funcionario = new Funcionario();
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario result = funcionarioService.create(funcionario);
        assertNotNull(result);
        verify(funcionarioRepository, times(1)).save(funcionario);

    }

    @Test
    void update() {
        String id = "1";
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        // Call the service method
        Funcionario result = funcionarioService.update(id, funcionario);

        // Verify and assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(funcionarioRepository, times(1)).findById(id);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    void delete() {

        String id = "1";
        Funcionario funcionaria = new Funcionario();
        funcionaria.setId(id);

        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionaria));


        doNothing().when(funcionarioRepository).deleteById("1");

        funcionarioService.delete("1");

        verify(funcionarioRepository, times(1)).deleteById("1");
    }

    @Test
    void findByIds() {

        String[] ids = {"1", "2"};
        List<Funcionario> funcionarios = Arrays.asList(new Funcionario(), new Funcionario());

        when(funcionarioRepository.findAllById(Arrays.asList(ids))).thenReturn(funcionarios);

        Set<Funcionario> result = funcionarioService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(funcionarioRepository, times(1)).findAllById(Arrays.asList(ids));

    }


    @Test
    void findByNome() {

        String nome = "Ana Carolina";
        Funcionario departamento = new Funcionario();
        departamento.setNome(nome);

        List<Funcionario> departamentos = Arrays.asList(departamento);
        when(funcionarioRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(departamentos);

        List<Funcionario> result = funcionarioService.findByNome(nome);

        // Verifica e faz o assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(nome, result.get(0).getNome());
        verify(funcionarioRepository, times(1)).findByNomeContainingIgnoreCase(nome);

    }
}