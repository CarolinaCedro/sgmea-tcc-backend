package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tcc.sgmeabackend.model.enums.UserRole;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Data
@Entity
public class Pessoa implements UserDetails {


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

    public Pessoa() {
        super();
    }

    public Pessoa(String id, String nome, String cpf, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public Pessoa(String id, String nome, String cpf, String email, String senha, UserRole role) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    //Autenticação

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_GESTOR"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_TECNICO")

            );
        }

        if (this.role == UserRole.toEnum(1)) {
            return List.of(new SimpleGrantedAuthority("ROLE_GESTOR"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_TECNICO")
            );
        }

        if (this.role == UserRole.toEnum(3)) {
            return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"));
        }


        return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
