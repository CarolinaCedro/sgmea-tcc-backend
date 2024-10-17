package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tcc.sgmeabackend.model.enums.UserRole;
import tcc.sgmeabackend.model.jackson.desserializer.GestorDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.GestorSerializer;

import java.time.LocalDateTime;
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
    @NotNull(message = "O campo cpf é requerido")
    private String cpf;

    @Email
    @NotNull(message = "O campo EMAIL é requerido")
    @Column(unique = true)
    @NotEmpty
    private String email;


    // Campo para armazenar o token de recuperação de senha
    @JsonIgnore
    private String resetToken;

    // Campo para armazenar a data de expiração do token
    @JsonIgnore
    private LocalDateTime resetTokenExpiryDate;


    @ManyToOne
    @JoinColumn(name = "gestor_id")
    @JsonSerialize(using = GestorSerializer.class)
    @JsonDeserialize(using = GestorDesserializer.class)
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

    public User(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    @JsonIgnore
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
        if (this.role == UserRole.FUNCIONARIO) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
    }

    @JsonProperty("authorities")
    public List<String> getRoles() {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }


    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }


    @Override
    @JsonIgnore
    public String getUsername() {
        return nome;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public void setResetToken(String token) {
        this.resetToken = token;
        this.resetTokenExpiryDate = LocalDateTime.now().plusHours(1); // Expira em 1 hora, por exemplo
    }

}
