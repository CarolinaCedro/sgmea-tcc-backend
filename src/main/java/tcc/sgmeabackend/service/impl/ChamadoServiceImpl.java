package tcc.sgmeabackend.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.repository.ChamadoCriadoRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoServiceImpl extends AbstractService<ChamadoCriado> {

    private final ChamadoCriadoRepository chamadoCriadoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final GestorRepository gestorRepository;
    private final ChamadoAtribuidoRepository chamadoAtribuidoRepository;

    private final ModelMapper modelMapper;


    public ChamadoServiceImpl(ChamadoCriadoRepository chamadoCriadoRepository, TecnicoRepository tecnicoRepository, GestorRepository gestorRepository, ChamadoAtribuidoRepository chamadoAtribuidoRepository, ModelMapper modelMapper) {
        this.chamadoCriadoRepository = chamadoCriadoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.gestorRepository = gestorRepository;
        this.chamadoAtribuidoRepository = chamadoAtribuidoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    protected JpaRepository<ChamadoCriado, String> getRepository() {
        return chamadoCriadoRepository;
    }


    public List<ChamadoCriado> getChamadosEncerrados() {
        // Aqui você implementa a lógica para buscar apenas os chamados encerrados
        return chamadoCriadoRepository.findByStatus(Status.ENCERRADO);
    }





    public ChamadoAtribuido atribuirChamado(ChamadoAtribuido chamadoAtribuidoResponse) {
        ChamadoCriado chamadoCriado = chamadoCriadoRepository.findById(chamadoAtribuidoResponse.getChamadoCriado().getId()).orElseThrow();
        Tecnico tecnico = tecnicoRepository.findById(chamadoAtribuidoResponse.getTecnico().getId()).orElseThrow();
        Gestor gestor = gestorRepository.findById(chamadoAtribuidoResponse.getGestor().getId()).orElseThrow();

        chamadoCriado.setPrioridade(chamadoAtribuidoResponse.getChamadoCriado().getPrioridade());
        chamadoCriado.setStatus(Status.ANDAMENTO);
        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoAtribuido.setChamadoCriado(chamadoCriado);
        chamadoAtribuido.setTecnico(tecnico);
        chamadoAtribuido.setGestor(gestor);

        return chamadoAtribuidoRepository.save(chamadoAtribuido);
    }

    public ChamadoConsolidado consolidarChamado(ChamadoAtribuido chamadoAtribuido) {
        String idChamado = chamadoAtribuido.getChamadoCriado().getId();
        Optional<ChamadoCriado> criado = this.findById(idChamado);
        if (criado.isPresent()) {
            ChamadoCriado existente = criado.get();
            ChamadoCriado chamadoCriado = chamadoAtribuido.getChamadoCriado();

            // Atualizando apenas os campos fornecidos no payload
            if (chamadoCriado.getDataFechamento() != null) {
                existente.setDataFechamento(chamadoCriado.getDataFechamento());
            }
            if (chamadoCriado.getStatus() != null) {
                existente.setStatus(chamadoCriado.getStatus());
            }
            if (chamadoCriado.getObservacaoConsolidacao() != null) {
                existente.setObservacaoConsolidacao(chamadoCriado.getObservacaoConsolidacao());
            }

            this.create(existente);


            //Ajustar o retorno com o dto adequado
            return null;
        }

        return null;
    }


    @Override
    public ChamadoCriado update(String id, ChamadoCriado resource) {
        Optional<ChamadoCriado> chamadoCriado = this.findById(id);
        if (chamadoCriado.isPresent()) {
            ChamadoCriado chamado = chamadoCriado.get();
            chamado.setId(id);
            chamado.setPrioridade(resource.getPrioridade());
            chamado.setStatus(resource.getStatus());
            chamado.setEquipamento(resource.getEquipamento());
            chamado.setTitulo(resource.getTitulo());
            chamado.setDataFechamento(resource.getDataFechamento());
            chamado.setObservacoes(resource.getObservacoes());
            chamado.setAlocado(resource.isAlocado());
            chamado.setFuncionario(resource.getFuncionario());
            chamado.setDataAbertura(resource.getDataAbertura());
            chamado.setObservacaoConsolidacao(resource.getObservacaoConsolidacao());
            return this.chamadoCriadoRepository.save(chamado);


        }
        return null;
    }

    public List findAllByStatusNot(Status status) {
        return this.chamadoCriadoRepository.findAllByStatusNotAndAlocadoIsFalse(status);
    }
}
