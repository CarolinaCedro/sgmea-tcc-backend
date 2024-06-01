package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.dtos.FuncionarioResponse;
import tcc.sgmeabackend.model.dtos.GestorResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/gestor")
public class GestorController extends AbstractController<Gestor, GestorResponse> {

    private final GestorServiceImpl service;

    public GestorController(GestorServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<GestorResponse> getDtoClass() {
        return GestorResponse.class;
    }

    @Override
    protected AbstractService<Gestor> getService() {
        return service;
    }


    @Override
    public ResponseEntity<GestorResponse> create(@RequestBody Gestor resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia");
        }
        return super.create(resource);
    }
}
