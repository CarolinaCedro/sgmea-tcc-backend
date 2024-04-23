package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import tcc.sgmeabackend.model.dtos.ClienteDTO;
import tcc.sgmeabackend.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Funcionario(String id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }


}
