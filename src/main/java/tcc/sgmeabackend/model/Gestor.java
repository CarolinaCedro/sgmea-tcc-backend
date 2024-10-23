package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.enums.AreaGestao;
import tcc.sgmeabackend.model.enums.UserRole;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoAtribuidoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoAtribuidoSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Gestor extends User {

    private static final long serialVersionUID = 1L;

//    @Enumerated(EnumType.STRING)
    private String areaGestao;

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", fetch = FetchType.EAGER)
    @JsonSerialize(using = ChamadoAtribuidoSerializer.class)
    @JsonDeserialize(using = ChamadoAtribuidoDesserializer.class)
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", fetch = FetchType.LAZY)
    private List<User> usuariosAlocados = new ArrayList<>();

    public Gestor(String id, @NotNull(message = "O campo NOME é requerido") String nome, String cpf, String email, String resetToken, LocalDateTime resetTokenExpiryDate, Gestor gestor, Perfil perfil, String senha, UserRole role, String areaGestao, List<ChamadoAtribuido> chamadoAtribuidos, List<User> usuariosAlocados) {
        super(id, nome, cpf, email, resetToken, resetTokenExpiryDate, gestor, perfil, senha, role);
        this.areaGestao = areaGestao;
        this.chamadoAtribuidos = chamadoAtribuidos;
        this.usuariosAlocados = usuariosAlocados;
    }
}
