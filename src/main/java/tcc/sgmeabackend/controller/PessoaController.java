package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.PessoaServiceImpl;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/pessoa")
public class PessoaController extends AbstractController<Pessoa> {

    private final PessoaServiceImpl service;

    public PessoaController(PessoaServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<Pessoa> getService() {
        return service;
    }
}
