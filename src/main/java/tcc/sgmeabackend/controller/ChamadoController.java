package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Chamado;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<Chamado> {

    private final ChamadoServiceImpl service;

    public ChamadoController(ChamadoServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<Chamado> getService() {
        return service;
    }
}
