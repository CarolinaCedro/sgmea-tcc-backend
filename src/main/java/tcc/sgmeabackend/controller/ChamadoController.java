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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<ChamadoCriado, ChamadoCriado> {

    private final ChamadoServiceImpl service;

    private final ChamadoAtribuidoRepository chamadoAtribuidoRepository;
    private final GestorServiceImpl gestorService;
    private final FuncionarioServiceImpl funcionarioService;

    private final EmailService emailService;

    public ChamadoController(ChamadoServiceImpl service, ModelMapper modelMapper, ChamadoAtribuidoRepository chamadoAtribuidoRepository, GestorServiceImpl gestorService, FuncionarioServiceImpl funcionarioService, EmailService emailService) {
        super(modelMapper);
        this.service = service;
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
    public ResponseEntity<Optional<ChamadoCriado>> chamadoAtribuidoById(@PathVariable String id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping("/chamados-atribuidos")
    public ResponseEntity<PageableResource<ChamadoAtribuido>> chamadoAtribuidos() {
        Status statusToExclude = Status.CONCLUIDO;
        return ResponseEntity.ok(toPageableResource(statusToExclude));
    }


    @Override
    @GetMapping
    public ResponseEntity<PageableResource<ChamadoCriado>> list(HttpServletResponse response, Map<String, String> allRequestParams) {
        Status statusToExclude = Status.ENCERRADO;
        return ResponseEntity.ok(toPageableResource(statusToExclude));
    }

    @GetMapping("/chamados-encerrados")
    public ResponseEntity<PageableResource<ChamadoCriado>> chamadosEncerrados() {
        List<ChamadoCriado> chamados = this.service.getChamadosEncerrados();
        return ResponseEntity.ok(new PageableResource(chamados));
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
        emailService.chamadoCriado(funcionario.getEmail(), gestor.getEmail());


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
