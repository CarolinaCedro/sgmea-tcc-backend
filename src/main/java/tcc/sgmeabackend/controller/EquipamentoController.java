package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.dtos.EquipamentoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.EquipamentoServiceImpl;

import java.util.List;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/equipamento")
public class EquipamentoController extends AbstractController<Equipamento, Equipamento> {

    private final EquipamentoServiceImpl service;

    public EquipamentoController(EquipamentoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<Equipamento> getDtoClass() {
        return Equipamento.class;
    }

    @Override
    protected AbstractService<Equipamento> getService() {
        return service;
    }

    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<Equipamento>> listAdvanced(
            @RequestParam(name = "nome", required = false) String nome) {

        List<Equipamento> list;

        // Verifica se o nome está vazio ou nulo
        if (nome != null && !nome.trim().isEmpty()) {
            // Se o nome não for nulo ou vazio, realiza a busca pelo nome
            list = this.service.findByNome(nome);
        } else {
            // Se o nome for nulo ou vazio, retorna todos os funcionários
            list = this.service.findAll();
        }

        return ResponseEntity.ok(new PageableResource<>(list));
    }
}
