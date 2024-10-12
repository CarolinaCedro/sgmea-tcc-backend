package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.repository.DepartamentoRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoServiceImpl extends AbstractService<Departamento> {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }


    @Override
    public Departamento update(String id, Departamento resource) {
        Optional<Departamento> departamentoCriado = this.findById(id);
        if (departamentoCriado.isPresent()) {
            Departamento departamento = departamentoCriado.get();
            departamento.setId(id);
            departamento.setNome(resource.getNome());
            departamento.setDescricao(resource.getDescricao());
            return this.departamentoRepository.save(departamento);
        }
        return null;
    }

    @Override
    protected JpaRepository<Departamento, String> getRepository() {
        return departamentoRepository;
    }

    public List<Departamento> findByNome(String nome) {
        return this.departamentoRepository.findByNomeContainingIgnoreCase(nome);

    }
}
