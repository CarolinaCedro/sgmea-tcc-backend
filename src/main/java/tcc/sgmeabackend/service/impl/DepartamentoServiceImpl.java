package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.repository.DepartamentoRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class DepartamentoServiceImpl extends AbstractService<Departamento> {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }


    @Override
    protected JpaRepository<Departamento, String> getRepository() {
        return departamentoRepository;
    }
}
