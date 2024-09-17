package tcc.sgmeabackend.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.model.dtos.ReportFilter;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.repository.ChamadoCriadoRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.time.LocalDate;
import java.util.Arrays;
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
        // Cria uma lista com os status ENCERRADO e CONCLUIDO
        List<Status> statusEncerrados = Arrays.asList(Status.ENCERRADO, Status.CONCLUIDO);

        return chamadoCriadoRepository.findByStatusIn(statusEncerrados);
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

    public ChamadoConsolidado consolidarChamado(String id, String observacaoConsolidacao) {
        Optional<ChamadoAtribuido> chamadoAtribuidoOpt = this.chamadoAtribuidoRepository.findById(id);

        if (chamadoAtribuidoOpt.isPresent()) {
            ChamadoAtribuido chamadoAtribuido = chamadoAtribuidoOpt.get();

            Optional<ChamadoCriado> chamadoCriadoOpt = this.chamadoCriadoRepository.findById(chamadoAtribuido.getChamadoCriado().getId());
            if (chamadoCriadoOpt.isPresent()) {

                ChamadoCriado chamadoCriado = chamadoCriadoOpt.get();

                if (chamadoCriado.getStatus() == null) {
                    chamadoCriado.setStatus(Status.CONCLUIDO);
                }

                chamadoCriado.setStatus(Status.CONCLUIDO);


                if (chamadoCriado.getPrioridade() == null) {
                    chamadoCriado.setPrioridade(Prioridade.MEDIA);
                }


                LocalDate dataFechamento = LocalDate.now();
                chamadoCriado.setDataFechamento(dataFechamento);

                // Verifica e define observação de consolidação
                if (observacaoConsolidacao == null || observacaoConsolidacao.isEmpty()) {
                    observacaoConsolidacao = "Não informado observações de consolidação";
                }
                chamadoCriado.setObservacaoConsolidacao(observacaoConsolidacao);


                // Salva as alterações no banco de dados
                try {
                    this.chamadoCriadoRepository.save(chamadoCriado);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao salvar chamado criado: " + e.getMessage());
                }

                // Cria e popula o ChamadoConsolidado
                ChamadoConsolidado consolidado = new ChamadoConsolidado();
                consolidado.setId(chamadoCriado.getId());
                consolidado.setDataAbertura(chamadoCriado.getDataAbertura());
                consolidado.setDataFechamento(chamadoCriado.getDataFechamento());
                consolidado.setPrioridade(chamadoCriado.getPrioridade());
                consolidado.setStatus(chamadoCriado.getStatus().toString());
                consolidado.setEquipamento(chamadoCriado.getEquipamento());
                consolidado.setTitulo(chamadoCriado.getTitulo());
                consolidado.setObservacaoConsolidacao(chamadoCriado.getObservacaoConsolidacao());
                consolidado.setObservacoes(chamadoCriado.getObservacoes());
                consolidado.setFuncionario(chamadoCriado.getFuncionario());
                consolidado.setGestor(chamadoAtribuido.getGestor());
                consolidado.setTecnico(chamadoAtribuido.getTecnico());

                return consolidado;
            }
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


    public List<ChamadoCriado> getChamadosConcluidosReport(ReportFilter filter) {
        Status statusConcluido = Status.CONCLUIDO;
        return chamadoCriadoRepository.findByStatusAndOptionalFilters(
                statusConcluido,
                filter.dataAbertura() != null ? filter.dataAbertura() : null,
                filter.dataFechamento() != null ? filter.dataFechamento() : null,
                filter.nomeEquipamento() != null && !filter.nomeEquipamento().isEmpty() ? filter.nomeEquipamento() : null);
    }



    public List<ChamadoCriado> getChamadosConcluidos() {
        Status statusConcluido = Status.CONCLUIDO;
        return chamadoCriadoRepository.findByStatus(statusConcluido);
    }


}
