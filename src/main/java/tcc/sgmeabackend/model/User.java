package tcc.sgmeabackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tcc.sgmeabackend.model.enums.UserRole;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @UuidGenerator
    private String id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    @ManyToOne
    @JoinColumn(name = "gestor_id")
    private Gestor gestor;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;


    private String senha;

    private UserRole role;



    public User(String id, String nome, String cpf, String email, String senha, UserRole role, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_GESTOR"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_TECNICO")

            );
        }

        if (this.role == UserRole.GESTOR) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_GESTOR"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_TECNICO")

            );
        }

        if (this.role == UserRole.TECNICO) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_TECNICO")
            );
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
