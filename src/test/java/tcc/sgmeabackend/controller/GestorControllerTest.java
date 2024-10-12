package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.Perfil;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GestorControllerTest {

    @InjectMocks
    private GestorController gestorController;

    @Mock
    private GestorServiceImpl gestorService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<Gestor> gestores = List.of(new Gestor(), new Gestor());
        PageableResource<Gestor> expectedPageableResource = new PageableResource<>(gestores);
        when(gestorService.findAll()).thenReturn(gestores);

        ResponseEntity<PageableResource<Gestor>> response = gestorController.list(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPageableResource, response.getBody());
    }

    @Test
    void findByIds() {
        Gestor gestor1 = new Gestor("1", "carolina", "70705599841", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, null, null);
        Gestor gestor2 = new Gestor("2", "carolina2", "70705599841", "carolemail@gmail.com", null, null, null, Perfil.FUNCIONARIO, "12345", null, null, null, null);
        Set<Gestor> gestores = Set.of(gestor1, gestor2);

        when(gestorService.findByIds(any(String[].class))).thenReturn(gestores);

        ResponseEntity<Set<Gestor>> response = gestorController.findByIds(new String[]{"1", "2"});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gestores, response.getBody());
    }

    @Test
    void findById() {
        String id = "1";
        Gestor gestor = new Gestor();
        gestor.setId(id);
        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));
        when(modelMapper.map(gestor, Gestor.class)).thenReturn(gestor);

        ResponseEntity<Optional<Gestor>> response = gestorController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void create() {
        Gestor gestor = new Gestor();
        Gestor gestorDto = new Gestor();
        gestorDto.setId("1");
        gestor.setSenha("12345");
        when(gestorService.create(any(Gestor.class))).thenReturn(gestor);
        when(modelMapper.map(gestor, Gestor.class)).thenReturn(gestorDto);

        ResponseEntity<Gestor> response = gestorController.create(gestor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gestorDto, response.getBody());
    }

    @Test
    void update() {
        String id = "1";
        Gestor gestor = new Gestor();
        Gestor gestorDto = new Gestor();

        when(gestorService.update(eq(id), any(Gestor.class))).thenReturn(gestorDto);
        when(modelMapper.map(gestor, Gestor.class)).thenReturn(gestorDto);

        ResponseEntity<Gestor> response = gestorController.update(id, gestor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(gestorDto, response.getBody());
    }

    @Test
    void delete() {
        String id = "1";
        doNothing().when(gestorService).delete(id);
        gestorController.delete(id);
        verify(gestorService, times(1)).delete(id);
    }
}
