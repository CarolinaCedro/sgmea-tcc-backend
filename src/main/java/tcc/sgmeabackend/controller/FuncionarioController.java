package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

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
}
