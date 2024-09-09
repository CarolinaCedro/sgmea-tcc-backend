package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

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
}
