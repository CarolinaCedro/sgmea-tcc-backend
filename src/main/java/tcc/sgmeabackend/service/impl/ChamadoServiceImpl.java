package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Chamado;
import tcc.sgmeabackend.repository.ChamadoRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class ChamadoServiceImpl extends AbstractService<Chamado> {

    private final ChamadoRepository chamadoRepository;

    public ChamadoServiceImpl(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }


    @Override
    protected JpaRepository<Chamado, String> getRepository() {
        return chamadoRepository;
    }
}
