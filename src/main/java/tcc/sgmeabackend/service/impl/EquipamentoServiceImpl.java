package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.repository.EquipamentoRepository;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class EquipamentoServiceImpl extends AbstractService<Equipamento> {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoServiceImpl(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }


    @Override
    protected JpaRepository<Equipamento, String> getRepository() {
        return equipamentoRepository;
    }
}
