package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tcc.sgmeabackend.model.dtos.TecnicoDTO;
import tcc.sgmeabackend.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Tecnico extends Pessoa {


//    @Id
//    @UuidGenerator
//    private String id;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.EAGER)
    private List<Chamado> chamados = new ArrayList<>();


    public Tecnico() {
        super();
    }

    public Tecnico(String id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }




}
