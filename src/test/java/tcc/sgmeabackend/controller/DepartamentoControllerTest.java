package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.service.impl.DepartamentoServiceImpl;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartamentoControllerTest {

    @InjectMocks
    private DepartamentoController departamentoController;

    @Mock
    private DepartamentoServiceImpl departamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<Departamento> departamentos = List.of(new Departamento());
        when(departamentoService.findAll()).thenReturn(departamentos);
        ResponseEntity<PageableResource<Departamento>> response = departamentoController.list(null,null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departamentos, response.getBody());
    }



    @Test
    void findByIds() {
        String[] ids = {"2", "1"};  // Declaração de array de strings
        List<Departamento> departamentos = List.of(new Departamento(), new Departamento());
        when(departamentoService.findByIds(any(String[].class))).thenReturn((Set<Departamento>) departamentos);

        ResponseEntity<List<Departamento>> response = departamentoController.findByIds(ids);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departamentos, response.getBody());
    }

    @Test
    void findById() {
        String id = "1";
        Departamento departamento = new Departamento();
        when(departamentoService.findById(id)).thenReturn(Optional.of(departamento));

        ResponseEntity<Optional<Departamento>> response = departamentoController.findById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(departamento, response.getBody().get());
    }

    @Test
    void create() {
        Departamento departamento = new Departamento();
        when(departamentoService.create(any(Departamento.class))).thenReturn(departamento);

        ResponseEntity<Departamento> response = departamentoController.create(departamento);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(departamento, response.getBody());
    }

    @Test
    void update() {
        String id = "1";
        Departamento departamento = new Departamento();
        when(departamentoService.update(id, departamento)).thenReturn(Optional.of(departamento));

        ResponseEntity<Optional<Departamento>> response = departamentoController.update(id, departamento);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(departamento, response.getBody().get());
    }

    @Test
    void delete() {
        String id = "1";
        // Configuração do Mockito para garantir que o método delete não faz nada
        doNothing().when(departamentoService).delete(id);

        // Chamada ao método delete no controlador
        ResponseEntity<Void> response = departamentoController.delete(id);

        // Verificação do status da resposta
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificação se o método delete foi chamado uma vez
        verify(departamentoService, times(1)).delete(id);
    }
}
