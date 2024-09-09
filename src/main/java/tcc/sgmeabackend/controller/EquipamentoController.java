package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.dtos.EquipamentoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.EquipamentoServiceImpl;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/equipamento")
public class EquipamentoController extends AbstractController<Equipamento, Equipamento> {

    private final EquipamentoServiceImpl service;

    public EquipamentoController(EquipamentoServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<Equipamento> getDtoClass() {
        return Equipamento.class;
    }

    @Override
    protected AbstractService<Equipamento> getService() {
        return service;
    }
}
