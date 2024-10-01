package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tcc.sgmeabackend.model.enums.UserRole;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoAtribuidoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoAtribuidoSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
public class Tecnico extends User {

    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name = "tecnico_especialidade",
            joinColumns = @JoinColumn(name = "tecnico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
//    @JsonSerialize(using = EquipamentoSerializer.class)
//    @JsonDeserialize(using = EspecialidadeDesserializer.class)
    private List<Especialidade> especialidades = new ArrayList<>();

    private boolean disponibilidade;



    @OneToMany(mappedBy = "tecnico", fetch = FetchType.EAGER)
    @JsonSerialize(using = ChamadoAtribuidoSerializer.class)
    @JsonDeserialize(using = ChamadoAtribuidoDesserializer.class)
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public Tecnico(String id, @NotNull(message = "O campo NOME é requerido") String nome, String cpf, String email, String resetToken, LocalDateTime resetTokenExpiryDate, Gestor gestor, Perfil perfil, String senha, UserRole role, List<Especialidade> especialidades, boolean disponibilidade, List<ChamadoAtribuido> chamadoAtribuidos) {
        super(id, nome, cpf, email, resetToken, resetTokenExpiryDate, gestor, perfil, senha, role);
        this.especialidades = especialidades;
        this.disponibilidade = disponibilidade;
        this.chamadoAtribuidos = chamadoAtribuidos;
    }

}
