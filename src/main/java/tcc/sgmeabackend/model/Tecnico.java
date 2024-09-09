package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Entity
public class Tecnico extends User {

    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name = "tecnico_especialidade",
            joinColumns = @JoinColumn(name = "tecnico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private List<Especialidade> especialidades = new ArrayList<>();

    private boolean disponibilidade;


    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.EAGER)
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public void consolidarChamado(ChamadoAtribuido chamadoAtribuido, String observacaoConsolidacao) {
//        ChamadoCriado chamadoCriado = chamadoAtribuido.getChamadoCriado();
//        chamadoCriado.finalizarChamado(observacaoConsolidacao);
        // Salvar alterações no banco de dados
    }
}
