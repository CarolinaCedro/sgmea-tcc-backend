package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.Perfil;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FuncionarioControllerTest {

    @InjectMocks
    private FuncionarioController funcionarioController;


    @Mock
    private FuncionarioServiceImpl funcionarioService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void list() {
        List<Funcionario> funcionario = List.of(new Funcionario(), new Funcionario());
        PageableResource<Funcionario> expectedPageableResource = new PageableResource<>(funcionario);
        when(funcionarioService.findAll()).thenReturn(funcionario);

        ResponseEntity<PageableResource<Funcionario>> response = funcionarioController.list(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPageableResource, response.getBody());
    }


    @Test
    void findByIds() {

        Funcionario func1 = new Funcionario("1", "carolina", "70705599841", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, "professora", null);
        Funcionario func2 = new Funcionario("2", "carolina2", "41626700800", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, "professora", null);
        Set<Funcionario> funcionario = Set.of(func1, func2);

        when(funcionarioService.findByIds(any(String[].class))).thenReturn(funcionario);

        ResponseEntity response = funcionarioController.findByIds(new String[]{"1", "2"});


        assertEquals(HttpStatus.OK, response.getStatusCode());


        assertEquals(funcionario, response.getBody());
    }

    @Test
    void findById() {
        String id = "1";
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        when(funcionarioService.findById(id)).thenReturn(Optional.of(funcionario));
        when(modelMapper.map(funcionario, Funcionario.class)).thenReturn(new Funcionario());

        ResponseEntity<Optional<Funcionario>> response = funcionarioController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void create() {
        Funcionario funcionario = new Funcionario();
        Funcionario funcionarioDto = new Funcionario();
        funcionarioDto.setSenha("12345");
        funcionario.setSenha("123456");
        when(funcionarioService.create(any(Funcionario.class))).thenReturn(funcionario);
        when(modelMapper.map(funcionario, Funcionario.class)).thenReturn(funcionarioDto);

        ResponseEntity<Funcionario> response = funcionarioController.create(funcionario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDto, response.getBody());
    }


    @Test
    void update() {
        String id = "1";
        Funcionario funcionario = new Funcionario();
        Funcionario funcionarioDto = new Funcionario();

        when(funcionarioService.update(eq(id), any(Funcionario.class))).thenReturn(funcionarioDto);

        when(modelMapper.map(funcionario, Funcionario.class)).thenReturn(funcionarioDto);

        ResponseEntity<Funcionario> response = funcionarioController.update(id, funcionario);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        assertEquals(funcionarioDto, response.getBody());
    }


    @Test
    void delete() {
        String id = "1";
        doNothing().when(funcionarioService).delete(id);
        funcionarioController.delete(id);
        verify(funcionarioService, times(1)).delete(id);
    }

}