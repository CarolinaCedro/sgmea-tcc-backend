package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Funcionario extends User {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    private String funcao;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
    private List<ChamadoCriado> chamadoCriados = new ArrayList<>();

    public Funcionario() {
        super();
    }



}
