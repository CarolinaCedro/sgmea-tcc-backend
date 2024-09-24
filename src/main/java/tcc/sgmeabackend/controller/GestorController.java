package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/sgmea/v1/gestor")
public class GestorController extends AbstractController<Gestor, Gestor> {

    private final GestorServiceImpl service;

    public GestorController(GestorServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<Gestor> getDtoClass() {
        return Gestor.class;
    }

    @Override
    protected AbstractService<Gestor> getService() {
        return service;
    }


    @Override
    public ResponseEntity<Gestor> create(@RequestBody Gestor resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia");
        }
        return super.create(resource);
    }

    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<Gestor>> listAdvanced(
            @RequestParam(name = "nome", required = false) String nome) {

        List<Gestor> list;

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
