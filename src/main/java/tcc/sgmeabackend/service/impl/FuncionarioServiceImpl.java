package tcc.sgmeabackend.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.exceptions.CpfAlocadoException;
import tcc.sgmeabackend.service.exceptions.EmailAlocadoException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

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
    public Funcionario create(Funcionario resource) {
        // Validação prévia de email e CPF
        Optional<User> existingUser = this.funcionarioRepository.findByEmail(resource.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlocadoException("Email já está alocado a outro usuário!");
        }

        Optional<User> existingUserForCpf = this.funcionarioRepository.findByCpf(resource.getCpf());
        if (existingUserForCpf.isPresent()) {
            throw new CpfAlocadoException("CPF já está alocado a outro usuário!");
        }

        try {
            // Tenta salvar o recurso
            return super.create(resource);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfAlocadoException("O CPF ou email informado já está em uso por outro usuário. Verifique os dados e tente novamente.");

        }
    }

    @Override
    protected JpaRepository<Funcionario, String> getRepository() {
        return funcionarioRepository;
    }
}
