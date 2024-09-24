package tcc.sgmeabackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.model.*;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.repository.ChamadoAtribuidoRepository;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.EmailService;
import tcc.sgmeabackend.service.exceptions.ResourceNotFoundException;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<ChamadoCriado, ChamadoCriado> {

    private final ChamadoServiceImpl service;

    public final ModelMapper modelMapper;


    private final ChamadoAtribuidoRepository chamadoAtribuidoRepository;
    private final GestorServiceImpl gestorService;
    private final FuncionarioServiceImpl funcionarioService;

    private final EmailService emailService;

    public ChamadoController(ChamadoServiceImpl service, ModelMapper modelMapper, ModelMapper modelMapper1, ChamadoAtribuidoRepository chamadoAtribuidoRepository, GestorServiceImpl gestorService, FuncionarioServiceImpl funcionarioService, EmailService emailService) {
        super(modelMapper);
        this.service = service;
        this.modelMapper = modelMapper1;
        this.chamadoAtribuidoRepository = chamadoAtribuidoRepository;
        this.gestorService = gestorService;
        this.funcionarioService = funcionarioService;
        this.emailService = emailService;
    }

    @PostMapping("/atribuir-chamado")
    public void atribuirChamado(@RequestBody ChamadoAtribuido chamadoAtribuidoDto) {
        this.service.atribuirChamado(chamadoAtribuidoDto);

    }


    @GetMapping("/chamados-atribuidos/{id}")
    public ResponseEntity<Optional<ChamadoAtribuido>> chamadoAtribuidoById(@PathVariable String id) {
        return ResponseEntity.ok(this.chamadoAtribuidoRepository.findById(id));
    }

    @GetMapping("/chamados-atribuidos")
    public ResponseEntity<PageableResource<ChamadoAtribuido>> chamadoAtribuidos() {
        final List<ChamadoAtribuido> records = this.service.findAllChamadosAlocados();
        return ResponseEntity.ok(new PageableResource<>(records));

    }


    @GetMapping("/list-advanced")
    public ResponseEntity<PageableResource<ChamadoCriado>> listAdvanced(
            @RequestParam(name = "titulo", required = false) String titulo) {

        List<Status> status = Arrays.asList(Status.ENCERRADO, Status.CONCLUIDO);
        List<String> listStringStatus = Arrays.asList(Status.ENCERRADO.toString(), Status.CONCLUIDO.toString());


        List<ChamadoCriado> list;

        // Verifica se o titulo está vazio ou nulo
        if (titulo != null && !titulo.trim().isEmpty()) {
            // Se o titulo não for nulo ou vazio, realiza a busca pelo titulo
            list = this.service.findAllByTituloContainingAndStatusNotInAndAlocadoFalse(titulo, status);
        } else {
            // Se o nome for nulo ou vazio, retorna todos os funcionários
            list = this.service.findAllByStatusNotAndAlocadoFalse(status);
        }

        return ResponseEntity.ok(new PageableResource<>(list));
    }


    @Override
    @GetMapping
    public ResponseEntity<PageableResource<ChamadoCriado>> list(HttpServletResponse response, Map<String, String> allRequestParams) {
        List<Status> status = Arrays.asList(Status.ENCERRADO, Status.CONCLUIDO);

        final List<ChamadoCriado> records = this.service.findAllByStatusNotAndAlocadoFalse(status);
        return ResponseEntity.ok(new PageableResource<>(records));
    }

    @GetMapping("/chamados-encerrados")
    public ResponseEntity<PageableResource<ChamadoCriado>> chamadosEncerrados() {
        List<ChamadoCriado> chamados = this.service.getChamadosEncerrados();
        return ResponseEntity.ok(new PageableResource(chamados));
    }

    @GetMapping("/chamados-encerrados/list-advanced")
    public ResponseEntity<PageableResource<ChamadoCriado>> listAdvancedByConcluido(
            @RequestParam(name = "titulo", required = false) String titulo) {

        List<ChamadoCriado> list;

        // Cria uma lista com os status ENCERRADO e CONCLUIDO
        List<Status> statusEncerrados = Arrays.asList(Status.ENCERRADO, Status.CONCLUIDO);

        // Verifica se o titulo está vazio ou nulo
        if (titulo != null && !titulo.trim().isEmpty()) {
            // Se o titulo não for nulo ou vazio, realiza a busca por título e status "Encerrado/Concluído"
            list = this.service.findByTituloContainingAndStatusIn(titulo, statusEncerrados);
        } else {
            // Se o título for nulo ou vazio, retorna todos os chamados com status "Encerrado/Concluído"
            list = this.service.getChamadosEncerrados();
        }

        return ResponseEntity.ok(new PageableResource<>(list));
    }


    @GetMapping("/chamados-concluidos")
    public ResponseEntity<PageableResource<ChamadoReportResponse>> chamadosConcluidos() {

        List<ChamadoCriado> chamados = this.service.getChamadosConcluidos();

        // Mapeando cada ChamadoCriado para ChamadoReportResponse
        List<ChamadoReportResponse> chamadosResponse = chamados.stream()
                .map(chamado -> modelMapper.map(chamado, ChamadoReportResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageableResource<>(chamadosResponse));
    }


    public PageableResource toPageableResource(final Status status) {
        final List records = this.service.findAllByStatusNot(status);
        return new PageableResource(records);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Optional<ChamadoCriado> existingResource = this.service.findById(id);
        if (existingResource.isPresent()) {
            ChamadoCriado resource = existingResource.get();
            // Supondo que há um método setStatus no seu modelo
            resource.setStatus(Status.ENCERRADO);
            this.service.create(resource);
        } else {
            throw new ResourceNotFoundException("Resource with id " + id + " not found");
        }
    }


    @PutMapping("/consolidacao-chamado/{id}")
    public ResponseEntity<ChamadoConsolidado> consolidarChamado(@PathVariable String id, @RequestBody String observacaoConsolidacao) {
        ChamadoConsolidado consolidado = this.service.consolidarChamado(id, observacaoConsolidacao);

        if (consolidado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consolidado);
    }

    @Override
    public ResponseEntity<ChamadoCriado> create(@RequestBody ChamadoCriado resource) {
        String funcionarioId = resource.getFuncionario().getId();
        Optional<Funcionario> funcionarioOpt = funcionarioService.findById(funcionarioId);

        if (funcionarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ChamadoCriado("Funcionário não encontrado"));
        }

        Funcionario funcionario = funcionarioOpt.get();
        String gestorId = funcionario.getGestor().getId();
        Optional<Gestor> gestorOpt = gestorService.findById(gestorId);

        if (gestorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ChamadoCriado("Funcionário não encontrado"));
        }

        Gestor gestor = gestorOpt.get();
        emailService.chamadoCriado(funcionario.getEmail(), gestor.getEmail(), resource.getObservacoes(),gestorOpt.get().getNome(),funcionarioOpt.get().getNome());


        return super.create(resource);
    }


    @Override
    protected Class<ChamadoCriado> getDtoClass() {
        return ChamadoCriado.class;
    }

    @Override
    protected AbstractService<ChamadoCriado> getService() {
        return service;
    }
}
