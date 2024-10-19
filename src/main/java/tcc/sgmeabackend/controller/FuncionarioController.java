package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/sgmea/v1/funcionario")
public class FuncionarioController extends AbstractController<Funcionario, Funcionario> {

    private final FuncionarioServiceImpl service;
    private final GestorServiceImpl gestorService;

    public FuncionarioController(FuncionarioServiceImpl service, ModelMapper modelMapper, GestorServiceImpl gestorService) {
        super(modelMapper);
        this.service = service;
        this.gestorService = gestorService;
    }

    @Override
    protected AbstractService<Funcionario> getService() {
        return service;
    }

    @Override
    protected Class<Funcionario> getDtoClass() {
        return Funcionario.class;
    }

    @Override
    public ResponseEntity<Funcionario> create(@RequestBody Funcionario resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia");
        }

        return super.create(resource);
    }




    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<Funcionario>> listAdvanced(
            @RequestParam(name = "nome", required = false) String nome) {

        List<Funcionario> list;

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
