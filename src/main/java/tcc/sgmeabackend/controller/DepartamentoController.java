package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.DepartamentoServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/sgmea/v1/departamento")
public class DepartamentoController extends AbstractController<Departamento, Departamento> {

    private final DepartamentoServiceImpl service;

    public DepartamentoController(DepartamentoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }


    @Override
    protected AbstractService<Departamento> getService() {
        return service;
    }

    @Override
    protected Class<Departamento> getDtoClass() {
        return Departamento.class;
    }


    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<Departamento>> listAdvanced(
            @RequestParam(name = "nome", required = false) String nome) {

        List<Departamento> list;

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
