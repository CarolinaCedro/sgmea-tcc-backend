package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.dtos.ChamadoAtribuidoDto;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.repository.ChamadoCriadoRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class ChamadoServiceImpl extends AbstractService<ChamadoCriado> {

    private final ChamadoCriadoRepository chamadoCriadoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final GestorRepository gestorRepository;
    private final ChamadoAtribuidoRepository chamadoAtribuidoRepository;

    public ChamadoServiceImpl(ChamadoCriadoRepository chamadoCriadoRepository, TecnicoRepository tecnicoRepository, GestorRepository gestorRepository, ChamadoAtribuidoRepository chamadoAtribuidoRepository) {
        this.chamadoCriadoRepository = chamadoCriadoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.gestorRepository = gestorRepository;
        this.chamadoAtribuidoRepository = chamadoAtribuidoRepository;
    }


    @Override
    protected JpaRepository<ChamadoCriado, String> getRepository() {
        return chamadoCriadoRepository;
    }


    public ChamadoAtribuido atribuirChamado(ChamadoAtribuidoDto chamadoAtribuidoResponse) {
        ChamadoCriado chamadoCriado = chamadoCriadoRepository.findById(chamadoAtribuidoResponse.chamadoId()).orElseThrow();
        Tecnico tecnico = tecnicoRepository.findById(chamadoAtribuidoResponse.tecnicoId()).orElseThrow();
        Gestor gestor = gestorRepository.findById(chamadoAtribuidoResponse.gestorId()).orElseThrow();

        chamadoCriado.setPrioridade(chamadoAtribuidoResponse.prioridade());
        chamadoCriado.setStatus(Status.ANDAMENTO);
        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoAtribuido.setChamadoCriado(chamadoCriado);
        chamadoAtribuido.setTecnico(tecnico);
        chamadoAtribuido.setGestor(gestor);

        return chamadoAtribuidoRepository.save(chamadoAtribuido);
    }

    public void consolidarChamado(String chamadoAtribuidoId, String observacaoConsolidacao) {
        ChamadoAtribuido chamadoAtribuido = chamadoAtribuidoRepository.findById(chamadoAtribuidoId).orElseThrow();
        Tecnico tecnico = chamadoAtribuido.getTecnico();
        tecnico.consolidarChamado(chamadoAtribuido, observacaoConsolidacao);
        chamadoCriadoRepository.save(chamadoAtribuido.getChamadoCriado());
    }
}
