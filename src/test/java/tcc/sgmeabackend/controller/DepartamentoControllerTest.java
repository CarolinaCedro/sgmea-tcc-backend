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
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.service.impl.DepartamentoServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class DepartamentoControllerTest {

    @InjectMocks
    private DepartamentoController departamentoController;


    @Mock
    private DepartamentoServiceImpl departamentoService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<Departamento> departamentos = List.of(new Departamento(), new Departamento());
        PageableResource<Departamento> expectedPageableResource = new PageableResource<>(departamentos);
        when(departamentoService.findAll()).thenReturn(departamentos);

        ResponseEntity<PageableResource<Departamento>> response = departamentoController.list(null, null);

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se os objetos PageableResource são iguais
        assertEquals(expectedPageableResource, response.getBody());
    }


    @Test
    void findByIds() {
        Departamento dept1 = new Departamento("1", "Nome1", "Descricao1");
        Departamento dept2 = new Departamento("2", "Nome2", "Descricao2");
        Set<Departamento> departamentos = Set.of(dept1, dept2); // Conjunto com dois objetos únicos

        when(departamentoService.findByIds(any(String[].class))).thenReturn(departamentos);

        ResponseEntity<Set<Departamento>> response = departamentoController.findByIds(new String[]{"1", "2"});

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o conjunto retornado é igual ao conjunto esperado
        assertEquals(departamentos, response.getBody());
    }


    @Test
    void findById() {
        String id = "1";
        Departamento departamento = new Departamento();
        departamento.setId(id);

        Departamento departamentoDto = new Departamento();
        when(departamentoService.findById(id)).thenReturn(Optional.of(departamento));
        when(modelMapper.map(departamento, Departamento.class)).thenReturn(departamentoDto);

        ResponseEntity<Optional<Departamento>> response = departamentoController.findById(id);

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o departamento é o esperado
        assertEquals(Optional.of(departamentoDto), response.getBody());
    }


    @Test
    void create() {
        Departamento departamento = new Departamento();
        Departamento departamentoDto = new Departamento();

        when(departamentoService.create(any(Departamento.class))).thenReturn(departamento);
        when(modelMapper.map(departamento, Departamento.class)).thenReturn(departamentoDto);

        ResponseEntity<Departamento> response = departamentoController.create(departamento);

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o departamentoDto é o esperado
        assertEquals(departamentoDto, response.getBody());
    }


    @Test
    void update() {
        String id = "1";
        Departamento departamento = new Departamento();
        Departamento departamentoDto = new Departamento();

        // Mock do serviço de atualização
        when(departamentoService.update(eq(id), any(Departamento.class))).thenReturn(departamentoDto);

        // Mock do mapeamento do ModelMapper
        when(modelMapper.map(departamento, Departamento.class)).thenReturn(departamentoDto);

        ResponseEntity<Departamento> response = departamentoController.update(id, departamento);

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o departamentoDto é o esperado
        Assertions.assertNotNull(response.getBody());
        assertEquals(departamentoDto, response.getBody());
    }



    @Test
    void delete() {
        String id = "1";
        // Configuração do Mockito para garantir que o método delete não faz nada
        doNothing().when(departamentoService).delete(id);
        // Chamada ao método delete no controlador
        departamentoController.delete(id);
        // Verificação se o método delete foi chamado uma vez
        verify(departamentoService, times(1)).delete(id);
    }


}
