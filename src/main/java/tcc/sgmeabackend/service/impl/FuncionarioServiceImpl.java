package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class FuncionarioServiceImpl extends AbstractService<Funcionario> {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }


    @Override
    protected JpaRepository<Funcionario, String> getRepository() {
        return funcionarioRepository;
    }
}
