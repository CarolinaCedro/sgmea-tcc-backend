package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.jackson.desserializer.DepartamentoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.DepartamentoSerializer;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
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
    private List<ChamadoCriado> chamadoCriados = new ArrayList<>();


}
