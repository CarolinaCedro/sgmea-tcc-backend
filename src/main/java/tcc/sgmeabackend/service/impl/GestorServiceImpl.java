package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class GestorServiceImpl extends AbstractService<Gestor> {

    private final GestorRepository gestorRepository;

    public GestorServiceImpl(GestorRepository gestorRepository) {
        this.gestorRepository = gestorRepository;
    }


    @Override
    protected JpaRepository<Gestor, String> getRepository() {
        return gestorRepository;
    }
}
