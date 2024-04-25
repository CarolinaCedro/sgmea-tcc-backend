package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import tcc.sgmeabackend.model.enums.UserRole;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {


    @Id
    @UuidGenerator
    private String id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;
    private String senha;


    private UserRole role;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao = LocalDate.now();

    public Pessoa(String id, String nome, String cpf, String email, String senha, UserRole role) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

}
