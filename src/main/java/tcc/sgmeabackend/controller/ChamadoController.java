package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.dtos.ChamadoAtribuidoDto;
import tcc.sgmeabackend.model.dtos.ChamadoCriadoResponse;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.EmailService;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;
import tcc.sgmeabackend.service.impl.FuncionarioServiceImpl;
import tcc.sgmeabackend.service.impl.GestorServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("api/sgmea/v1/chamado")
public class ChamadoController extends AbstractController<ChamadoCriado, ChamadoCriadoResponse> {

    private final ChamadoServiceImpl service;
    private final GestorServiceImpl gestorService;
    private final FuncionarioServiceImpl funcionarioService;

    private final EmailService emailService;

    public ChamadoController(ChamadoServiceImpl service, ModelMapper modelMapper, GestorServiceImpl gestorService, FuncionarioServiceImpl funcionarioService, EmailService emailService) {
        super(modelMapper);
        this.service = service;
        this.gestorService = gestorService;
        this.funcionarioService = funcionarioService;
        this.emailService = emailService;
    }

    @PostMapping("/atribuir-chamado")
    public void atribuirChamado(@RequestBody ChamadoAtribuidoDto chamadoAtribuidoDto) {
        this.service.atribuirChamado(chamadoAtribuidoDto);

    }

    @Override
    public ResponseEntity<ChamadoCriadoResponse> create(@RequestBody ChamadoCriado resource) {
        String funcionarioId = resource.getFuncionario().getId();
        Optional<Funcionario> funcionarioOpt = funcionarioService.findById(funcionarioId);

        if (funcionarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ChamadoCriadoResponse("Funcionário não encontrado"));
        }

        Funcionario funcionario = funcionarioOpt.get();
        String gestorId = funcionario.getGestor().getId();
        Optional<Gestor> gestorOpt = gestorService.findById(gestorId);

        if (gestorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ChamadoCriadoResponse("Funcionário não encontrado"));
        }

        Gestor gestor = gestorOpt.get();
        emailService.chamadoCriado(funcionario.getEmail(), gestor.getEmail());


        return super.create(resource);
    }


    @Override
    protected Class<ChamadoCriadoResponse> getDtoClass() {
        return ChamadoCriadoResponse.class;
    }

    @Override
    protected AbstractService<ChamadoCriado> getService() {
        return service;
    }
}
