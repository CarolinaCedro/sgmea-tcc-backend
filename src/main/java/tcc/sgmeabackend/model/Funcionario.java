package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UUID;
import tcc.sgmeabackend.model.dtos.ClienteDTO;
import tcc.sgmeabackend.model.enums.Perfil;

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
        addPerfil(Perfil.CLIENTE);
    }

    public Funcionario(String id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Funcionario(ClienteDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }


}
