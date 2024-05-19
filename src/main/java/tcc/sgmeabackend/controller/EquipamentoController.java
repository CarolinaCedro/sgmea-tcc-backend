package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.EquipamentoServiceImpl;
import tcc.sgmeabackend.service.impl.TecnicoServiceImpl;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/equipamento")
public class EquipamentoController extends AbstractController<Equipamento> {

    private final EquipamentoServiceImpl service;

    public EquipamentoController(EquipamentoServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<Equipamento> getService() {
        return service;
    }
}
