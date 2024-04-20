package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/tecnico")
public class TecnicoController extends AbstractController<Tecnico> {

    private final TecnicoServiceImpl service;

    public TecnicoController(TecnicoServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<Tecnico> getService() {
        return service;
    }
}
