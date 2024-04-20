package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class TecnicoServiceImpl extends AbstractService<Tecnico> {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }


    @Override
    protected JpaRepository<Tecnico, String> getRepository() {
        return tecnicoRepository;
    }
}
