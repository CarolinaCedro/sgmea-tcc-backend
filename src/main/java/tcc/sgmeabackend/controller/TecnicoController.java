package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.dtos.TecnicoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

import java.util.List;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/tecnico")
public class TecnicoController extends AbstractController<Tecnico, Tecnico> {

    private final TecnicoServiceImpl service;

    public TecnicoController(TecnicoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }


    @Override
    protected AbstractService<Tecnico> getService() {
        return service;
    }

    @Override
    protected Class<Tecnico> getDtoClass() {
        return Tecnico.class;
    }

    @Override
    public ResponseEntity<Tecnico> create(@RequestBody Tecnico resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia");
        }
        return super.create(resource);
    }


    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<Tecnico>> listAdvanced(
            @RequestParam(name = "nome", required = false) String nome) {

        List<Tecnico> list;

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
