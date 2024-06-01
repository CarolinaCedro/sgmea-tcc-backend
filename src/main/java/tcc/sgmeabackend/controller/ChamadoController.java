package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.dtos.ChamadoCriadoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<ChamadoCriado, ChamadoCriadoResponse> {

    private final ChamadoServiceImpl service;

    public ChamadoController(ChamadoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<ChamadoCriadoResponse> getDtoClass() {
        return ChamadoCriadoResponse.class;
    }

    @Override
    protected AbstractService<ChamadoCriado> getService() {
        return service;
    }
}
