package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.Perfil;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TecnicoControllerTest {

    @InjectMocks
    private TecnicoController tecnicoController;

    @Mock
    private TecnicoServiceImpl tecnicoService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<Tecnico> tecnicos = List.of(new Tecnico(), new Tecnico());
        PageableResource<Tecnico> expectedPageableResource = new PageableResource<>(tecnicos);
        when(tecnicoService.findAll()).thenReturn(tecnicos);

        ResponseEntity<PageableResource<Tecnico>> response = tecnicoController.list(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPageableResource, response.getBody());
    }

    @Test
    void findByIds() {
        Tecnico tecnico1 = new Tecnico("1", "carolina", "70705599841", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, true, null);
        Tecnico tecnico2 = new Tecnico("2", "carolina2", "70705599841", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, true, null);
        Set<Tecnico> tecnicos = Set.of(tecnico1, tecnico2);

        when(tecnicoService.findByIds(any(String[].class))).thenReturn(tecnicos);

        ResponseEntity<Set<Tecnico>> response = tecnicoController.findByIds(new String[]{"1", "2"});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tecnicos, response.getBody());
    }

    @Test
    void findById() {
        String id = "1";
        Tecnico tecnico = new Tecnico();
        tecnico.setId(id);
        when(tecnicoService.findById(id)).thenReturn(Optional.of(tecnico));
        when(modelMapper.map(tecnico, Tecnico.class)).thenReturn(tecnico);

        ResponseEntity<Optional<Tecnico>> response = tecnicoController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void create() {
        Tecnico tecnico = new Tecnico();
        Tecnico tecnicoDto = new Tecnico();
        tecnicoDto.setId("1");
        tecnico.setSenha("12345");
        when(tecnicoService.create(any(Tecnico.class))).thenReturn(tecnico);
        when(modelMapper.map(tecnico, Tecnico.class)).thenReturn(tecnicoDto);

        ResponseEntity<Tecnico> response = tecnicoController.create(tecnico);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tecnicoDto, response.getBody());
    }

    @Test
    void update() {
        String id = "1";
        Tecnico tecnico = new Tecnico();
        Tecnico tecnicoDto = new Tecnico();

        when(tecnicoService.update(eq(id), any(Tecnico.class))).thenReturn(tecnicoDto);
        when(modelMapper.map(tecnico, Tecnico.class)).thenReturn(tecnicoDto);

        ResponseEntity<Tecnico> response = tecnicoController.update(id, tecnico);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tecnicoDto, response.getBody());
    }

    @Test
    void delete() {
        String id = "1";
        doNothing().when(tecnicoService).delete(id);
        tecnicoController.delete(id);
        verify(tecnicoService, times(1)).delete(id);
    }
}
