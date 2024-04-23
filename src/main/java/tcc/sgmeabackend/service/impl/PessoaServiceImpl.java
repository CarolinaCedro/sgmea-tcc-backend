package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.repository.PessoaRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class PessoaServiceImpl extends AbstractService<Pessoa> {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    @Override
    protected JpaRepository<Pessoa, String> getRepository() {
        return pessoaRepository;
    }
}
