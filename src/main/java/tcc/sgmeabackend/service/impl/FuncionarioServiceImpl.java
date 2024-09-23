package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl extends AbstractService<Funcionario> {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public Funcionario update(String id, Funcionario resource) {
        Optional<Funcionario> funcionario = this.findById(id);
        if (funcionario.isPresent()) {
            Funcionario func = funcionario.get();
            func.setId(resource.getId());
            func.setGestor(resource.getGestor());
            func.setCpf(resource.getCpf());
            func.setSenha(resource.getSenha());
            func.setNome(resource.getNome());
            func.setEmail(resource.getEmail());
            func.setRole(resource.getRole());
            func.setFuncao(resource.getFuncao());
            func.setDepartamento(resource.getDepartamento());
            func.setChamadoCriados(resource.getChamadoCriados());
            return this.funcionarioRepository.save(func);
        }
        return null;
    }

    @Override
    protected JpaRepository<Funcionario, String> getRepository() {
        return funcionarioRepository;
    }

    public List<Funcionario> findByNome(String nome) {
        return this.funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}
