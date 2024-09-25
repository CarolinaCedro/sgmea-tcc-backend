package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tcc.sgmeabackend.model.enums.UserRole;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoCriadoDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.DepartamentoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoSerializer;
import tcc.sgmeabackend.model.jackson.serializer.DepartamentoSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
public class Funcionario extends User {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonSerialize(using = DepartamentoSerializer.class)
    @JsonDeserialize(using = DepartamentoDesserializer.class)
    private Departamento departamento;

    private String funcao;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonSerialize(using = ChamadoSerializer.class)
    @JsonDeserialize(using = ChamadoCriadoDesserializer.class)
    private List<ChamadoCriado> chamadoCriados = new ArrayList<>();


    public Funcionario(String id, @NotNull(message = "O campo NOME é requerido") String nome, String cpf, String email, String resetToken, LocalDateTime resetTokenExpiryDate, Gestor gestor, Perfil perfil, String senha, UserRole role, Departamento departamento, String funcao, List<ChamadoCriado> chamadoCriados) {
        super(id, nome, cpf, email, resetToken, resetTokenExpiryDate, gestor, perfil, senha, role);
        this.departamento = departamento;
        this.funcao = funcao;
        this.chamadoCriados = chamadoCriados;
    }




}
