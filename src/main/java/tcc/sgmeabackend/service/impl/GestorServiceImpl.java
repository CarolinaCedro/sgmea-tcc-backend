package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class GestorServiceImpl extends AbstractService<Gestor> {

    private final GestorRepository gestorRepository;

    public GestorServiceImpl(GestorRepository gestorRepository) {
        this.gestorRepository = gestorRepository;
    }


    @Override
    public Gestor update(String id, Gestor resource) {
        Optional<Gestor> gestor = this.findById(id);
        if (gestor.isPresent()) {
            Gestor gest = gestor.get();
            gest.setId(resource.getId());
            gest.setGestor(resource.getGestor());
            gest.setCpf(resource.getCpf());
            gest.setSenha(resource.getSenha());
            gest.setNome(resource.getNome());
            gest.setEmail(resource.getEmail());
            gest.setRole(resource.getRole());
            gest.setAreaGestao(resource.getAreaGestao());
            gest.setUsuariosAlocados(resource.getUsuariosAlocados());
            gest.setChamadoAtribuidos(resource.getChamadoAtribuidos());
            return this.gestorRepository.save(gest);
        }
        return null;
    }

    @Override
    protected JpaRepository<Gestor, String> getRepository() {
        return gestorRepository;
    }

    public List<Gestor> findByNome(String nome) {
        return this.gestorRepository.findByNomeContainingIgnoreCase(nome);
    }
}
