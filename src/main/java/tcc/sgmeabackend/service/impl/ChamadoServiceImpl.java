package tcc.sgmeabackend.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
import tcc.sgmeabackend.service.EmailService;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class ChamadoServiceImpl extends AbstractService<ChamadoCriado> {

    private final ChamadoCriadoRepository chamadoCriadoRepository;
    private final TecnicoRepository tecnicoRepository;

    private final EmailService emailService;

    private final GestorRepository gestorRepository;
    private final ChamadoAtribuidoRepository chamadoAtribuidoRepository;

    private final ModelMapper modelMapper;


    public ChamadoServiceImpl(ChamadoCriadoRepository chamadoCriadoRepository, TecnicoRepository tecnicoRepository, EmailService emailService, GestorRepository gestorRepository, ChamadoAtribuidoRepository chamadoAtribuidoRepository, ModelMapper modelMapper) {
        this.chamadoCriadoRepository = chamadoCriadoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.emailService = emailService;
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

        chamadoCriado.setStatus(Status.ANDAMENTO);
        chamadoCriado.setAlocado(true);
        chamadoCriado.setPrioridade(chamadoAtribuidoResponse.getPrioridade());
        ChamadoAtribuido chamadoAtribuido = new ChamadoAtribuido();
        chamadoAtribuido.setChamadoCriado(chamadoCriado);
        chamadoAtribuido.setTecnico(tecnico);
        chamadoAtribuido.setGestor(gestor);
        chamadoAtribuido.setPrioridade(chamadoAtribuidoResponse.getPrioridade());

        emailService.chamadoAtribuido(chamadoCriado.getFuncionario().getEmail(), chamadoAtribuido.getGestor().getEmail(), chamadoAtribuido);

        return chamadoAtribuidoRepository.save(chamadoAtribuido);
    }


    public int countChamadosByStatus(Status status) {

        try {
            // Passa o ordinal (número) do enum para a consulta
            return chamadoCriadoRepository.countByStatus(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("O status fornecido não corresponde a um valor válido da enumeração Status.");
        }
    }


    public int countChamadosCriticos() {
        return chamadoAtribuidoRepository.countChamadosCriticos();
    }

    public int countPropostasNovas() {
        return chamadoCriadoRepository.countPropostasNovas();
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


                if (chamadoAtribuido.getPrioridade() == null) {
                    chamadoAtribuido.setPrioridade(Prioridade.MEDIA);
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
                consolidado.setStatus(chamadoCriado.getStatus().toString());
                consolidado.setEquipamento(chamadoCriado.getEquipamento());
                consolidado.setTitulo(chamadoCriado.getTitulo());
                consolidado.setObservacaoConsolidacao(chamadoCriado.getObservacaoConsolidacao());
                consolidado.setObservacoes(chamadoCriado.getObservacoes());
                consolidado.setFuncionario(chamadoCriado.getFuncionario());
                consolidado.setGestor(chamadoAtribuido.getGestor());
                consolidado.setPrioridade(chamadoAtribuido.getPrioridade());
                consolidado.setTecnico(chamadoAtribuido.getTecnico());

                emailService.consolidacaoChamado(chamadoCriado.getFuncionario().getEmail(), chamadoAtribuido.getGestor().getEmail(), consolidado);


                return consolidado;
            }
        }

        return null;
    }


    public Map<String, Object> getChamadoChartData() {
        Map<String, Object> chartData = new HashMap<>();

        // Obter as datas de início e fim da semana atual e da semana passada
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfThisWeek = today.with(weekFields.dayOfWeek(), 1);  // Início desta semana
        LocalDate endOfThisWeek = today.with(weekFields.dayOfWeek(), 7);    // Fim desta semana

        LocalDate startOfLastWeek = startOfThisWeek.minusWeeks(1);  // Início da semana passada
        LocalDate endOfLastWeek = endOfThisWeek.minusWeeks(1);      // Fim da semana passada

        // Consultar os chamados desta semana e da semana passada
        int chamadosThisWeek = chamadoCriadoRepository.countChamadosByWeek(startOfThisWeek, endOfThisWeek, Status.CONCLUIDO);
        int chamadosLastWeek = chamadoCriadoRepository.countChamadosByWeek(startOfLastWeek, endOfLastWeek, Status.CONCLUIDO);

        // Consultar os 4 equipamentos com mais chamados abertos
        List<Map<String, Object>> topEquipamentos = chamadoCriadoRepository.findTopEquipamentosByChamados(Status.CONCLUIDO, PageRequest.of(0, 4));

        // Preencher os dados para o gráfico
        chartData.put("labels", topEquipamentos.stream().map(e -> e.get("equipamento")).toArray());
        chartData.put("thisWeek", new int[]{chamadosThisWeek});
        chartData.put("lastWeek", new int[]{chamadosLastWeek});

        return chartData;
    }


    @Override
    public ChamadoCriado update(String id, ChamadoCriado resource) {
        Optional<ChamadoCriado> chamadoCriado = this.findById(id);
        if (chamadoCriado.isPresent()) {
            ChamadoCriado chamado = chamadoCriado.get();
            chamado.setId(id);
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

    public List<ChamadoCriado> findAllByStatusNotAndAlocadoFalse(List<Status> status) {
        return this.chamadoCriadoRepository.findAllByStatusNotInAndAlocadoFalse(status);
    }

    public List<ChamadoAtribuido> findAllChamadosAlocados() {
        return this.chamadoAtribuidoRepository.findAllChamadosAlocadosSemConcluidos();
    }


    public List<ChamadoCriado> getChamadosConcluidosReport(ReportFilter filter) {
        Status statusConcluido = Status.CONCLUIDO;
        return chamadoCriadoRepository.findByStatusAndOptionalFilters(
                statusConcluido,
                filter.getDataAbertura() != null ? filter.getDataAbertura() : null,
                filter.getDataFechamento() != null ? filter.getDataFechamento() : null,
                filter.getNomeEquipamento() != null && !filter.getNomeEquipamento().isEmpty() ? filter.getNomeEquipamento() : null);
    }


    public List<ChamadoCriado> getChamadosConcluidos() {
        Status statusConcluido = Status.CONCLUIDO;
        return chamadoCriadoRepository.findByStatus(statusConcluido);
    }

    public List<ChamadoCriado> findByTituloContainingAndStatusIn(String titulo, List<Status> statusEncerrados) {
        return chamadoCriadoRepository.findByTituloContainingAndStatusIn(titulo, statusEncerrados);
    }


    public List<ChamadoCriado> findByTitulo(String titulo) {
        return this.chamadoCriadoRepository.findByTituloContainingIgnoreCase(titulo);

    }

    public List<ChamadoCriado> findAllByTituloContainingAndStatusNotInAndAlocadoFalse(String titulo, List<Status> status) {
        return this.chamadoCriadoRepository.findAllByTituloContainingAndStatusNotInAndAlocadoFalse(titulo, status);
    }


}
