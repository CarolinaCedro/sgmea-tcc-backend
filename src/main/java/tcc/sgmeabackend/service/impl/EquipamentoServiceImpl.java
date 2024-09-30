package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.repository.EquipamentoRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoServiceImpl extends AbstractService<Equipamento> {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoServiceImpl(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Override
    public Equipamento update(String id, Equipamento resource) {
        System.out.println("ID EQUIPAMENTO" + id);
        System.out.println("EQUIPAMENTO" + resource);


        Optional<Equipamento> equipamento = this.findById(id);
        if (equipamento.isPresent()) {
            Equipamento equip = equipamento.get();
            equip.setId(resource.getId());
            equip.setNome(resource.getNome());
            equip.setDescricao(resource.getDescricao());
            equip.setModelo(resource.getModelo());
            equip.setPatrimonio(resource.getPatrimonio());
            equip.setFabricante(resource.getFabricante());
            equip.setEmUso(resource.isEmUso());
            return this.equipamentoRepository.save(equip);
        }
        return null;
    }

    @Override
    protected JpaRepository<Equipamento, String> getRepository() {
        return equipamentoRepository;
    }

    public List<Equipamento> findByNome(String nome) {
        return this.equipamentoRepository.findByNomeContainingIgnoreCase(nome);

    }

    public long countEquipamentos() {
        return this.equipamentoRepository.count();
    }
}
