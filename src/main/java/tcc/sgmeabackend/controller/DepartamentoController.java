package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.dtos.DepartamentoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.DepartamentoServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/departamento")
public class DepartamentoController extends AbstractController<Departamento, DepartamentoResponse> {

    private final DepartamentoServiceImpl service;

    public DepartamentoController(DepartamentoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }


    @Override
    protected AbstractService<Departamento> getService() {
        return service;
    }

    @Override
    protected Class<DepartamentoResponse> getDtoClass() {
        return DepartamentoResponse.class;
    }
}
