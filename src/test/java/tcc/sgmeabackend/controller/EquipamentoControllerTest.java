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
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.service.impl.EquipamentoServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EquipamentoControllerTest {


    @InjectMocks
    private EquipamentoController equipamentoController;


    @Mock
    private EquipamentoServiceImpl equipamentoService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {

        List<Equipamento> equipamentos = List.of(new Equipamento(), new Equipamento());
        PageableResource<Equipamento> expectedPageableResource = new PageableResource<>(equipamentos);
        when(equipamentoService.findAll()).thenReturn(equipamentos);

        ResponseEntity<PageableResource<Equipamento>> response = equipamentoController.list(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPageableResource, response.getBody());
    }

    @Test
    void findByIds() {
        Equipamento equip1 = new Equipamento("1", "Ar condicionado", "aparelho", "JOIUN", "JULIYU9999", true);
        Equipamento equip2 = new Equipamento("2", "Cadeira", "cadeira", "JOIUN", "JULIYU9999", true);
        Set<Equipamento> equipamentos = Set.of(equip1, equip2); // Conjunto com dois objetos únicos

        when(equipamentoService.findByIds(any(String[].class))).thenReturn(equipamentos);

        ResponseEntity<Set<Equipamento>> response = equipamentoController.findByIds(new String[]{"1", "2"});

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o conjunto retornado é igual ao conjunto esperado
        assertEquals(equipamentos, response.getBody());

    }

    @Test
    void findById() {

        String id = "1";
        Equipamento equipamento = new Equipamento();
        equipamento.setId(id);

        Equipamento equipamentoDto = new Equipamento();
        when(equipamentoService.findById(id)).thenReturn(Optional.of(equipamento));
        when(modelMapper.map(equipamento, Equipamento.class)).thenReturn(equipamentoDto);

        ResponseEntity<Optional<Equipamento>> response = equipamentoController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(Optional.of(equipamentoDto), response.getBody());

    }

    @Test
    void create() {
        Equipamento equipamento = new Equipamento();
        Equipamento equipamentoDto = new Equipamento();

        when(equipamentoService.create(any(Equipamento.class))).thenReturn(equipamento);
        when(modelMapper.map(equipamento, Equipamento.class)).thenReturn(equipamentoDto);

        ResponseEntity<Equipamento> response = equipamentoController.create(equipamento);


        assertEquals(HttpStatus.OK, response.getStatusCode());


        assertEquals(equipamentoDto, response.getBody());
    }

    @Test
    void update() {
        String id = "1";
        Equipamento equipamento = new Equipamento();
        Equipamento equipamentoDto = new Equipamento();

        // Mock do serviço de atualização
        when(equipamentoService.update(eq(id), any(Equipamento.class))).thenReturn(equipamentoDto);

        // Mock do mapeamento do ModelMapper
        when(modelMapper.map(equipamento, Equipamento.class)).thenReturn(equipamentoDto);

        ResponseEntity<Equipamento> response = equipamentoController.update(id, equipamento);

        // Verifique o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifique se o departamentoDto é o esperado
        Assertions.assertNotNull(response.getBody());
        assertEquals(equipamentoDto, response.getBody());
    }

    @Test
    void delete() {
        String id = "1";
        doNothing().when(equipamentoService).delete(id);
        equipamentoController.delete(id);
        verify(equipamentoService, times(1)).delete(id);
    }
}