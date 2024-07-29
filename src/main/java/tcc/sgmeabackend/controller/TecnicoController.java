package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.dtos.TecnicoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/tecnico")
public class TecnicoController extends AbstractController<Tecnico, TecnicoResponse> {

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
    protected Class<TecnicoResponse> getDtoClass() {
        return TecnicoResponse.class;
    }

    @Override
    public ResponseEntity<TecnicoResponse> create(@RequestBody Tecnico resource) {
        if (resource.getSenha() != null && !resource.getSenha().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getSenha());
            resource.setSenha(encryptedPassword);
        } else {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia");
        }
        return super.create(resource);
    }
}
