package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Funcionario extends Pessoa {
    private static final long serialVersionUID = 1L;


//    @Id
//    @UuidGenerator
//    private String id;


    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
    private List<Chamado> chamados = new ArrayList<>();

    public Funcionario() {
        super();
    }



}
