package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import tcc.sgmeabackend.model.jackson.desserializer.DepartamentoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.DepartamentoSerializer;

import java.util.ArrayList;
import java.util.List;

@Data
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
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChamadoCriado> chamadoCriados = new ArrayList<>();

    public Funcionario() {
        super();
    }


}
