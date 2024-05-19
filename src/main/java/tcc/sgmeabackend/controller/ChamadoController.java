package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<ChamadoCriado> {

    private final ChamadoServiceImpl service;

    public ChamadoController(ChamadoServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<ChamadoCriado> getService() {
        return service;
    }
}
