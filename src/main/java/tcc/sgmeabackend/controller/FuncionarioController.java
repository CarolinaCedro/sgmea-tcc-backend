package tcc.sgmeabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/funcionario")
public class FuncionarioController extends AbstractController<Funcionario> {

    private final FuncionarioServiceImpl service;

    public FuncionarioController(FuncionarioServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<Funcionario> getService() {
        return service;
    }

    @Override
    public ResponseEntity<Funcionario> create(@RequestBody Funcionario resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return super.create(resource);
    }
}
