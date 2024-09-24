package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoServiceImpl extends AbstractService<Tecnico> {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }


    @Override
    public Tecnico update(String id, Tecnico resource) {
        Optional<Tecnico> tecnico = this.findById(id);
        if (tecnico.isPresent()) {
            Tecnico tec = tecnico.get();
            tec.setId(resource.getId());
            tec.setGestor(resource.getGestor());
            tec.setCpf(resource.getCpf());
            tec.setSenha(resource.getSenha());
            tec.setNome(resource.getNome());
            tec.setEmail(resource.getEmail());
            tec.setDisponibilidade(resource.isDisponibilidade());
            tec.setRole(resource.getRole());
            tec.setEspecialidades(resource.getEspecialidades());
            tec.setChamadoAtribuidos(resource.getChamadoAtribuidos());
            return this.tecnicoRepository.save(tec);
        }
        return null;
    }

    @Override
    protected JpaRepository<Tecnico, String> getRepository() {
        return tecnicoRepository;
    }

    public List<Tecnico> findByNome(String nome) {
        return this.tecnicoRepository.findByNomeContainingIgnoreCase(nome);

    }
}
