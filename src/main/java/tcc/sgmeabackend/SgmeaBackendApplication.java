package tcc.sgmeabackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tcc.sgmeabackend.controller.ChamadoController;
import tcc.sgmeabackend.controller.FuncionarioController;
import tcc.sgmeabackend.controller.TecnicoController;
import tcc.sgmeabackend.model.Chamado;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;

@SpringBootApplication
public class SgmeaBackendApplication implements CommandLineRunner {


    public SgmeaBackendApplication(TecnicoController tecnicoController, FuncionarioController funcionarioController, ChamadoController chamadoController) {
        this.tecnicoController = tecnicoController;
        this.funcionarioController = funcionarioController;
        this.chamadoController = chamadoController;
    }

    public static void main(String[] args) {
        SpringApplication.run(SgmeaBackendApplication.class, args);
    }

    private final TecnicoController tecnicoController;
    private final FuncionarioController funcionarioController;
    private final ChamadoController chamadoController;

    @Override
    public void run(String... args) throws Exception {


        Tecnico tecnico1 = new Tecnico(null, "Ana Carolina", "055.287.750-60", "carol@email.com", "1234");
        Funcionario funcionario1 = new Funcionario(null, "Lalisa Manoban", "701.450.640-42", "lalisa@email.com", "1234");

        Chamado chamado1 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Maxilar estralando e doí", "Pq isso do nada oxe ?!", tecnico1, funcionario1);

        funcionarioController.create(funcionario1);
        tecnicoController.create(tecnico1);
        chamadoController.create(chamado1);

    }
}
