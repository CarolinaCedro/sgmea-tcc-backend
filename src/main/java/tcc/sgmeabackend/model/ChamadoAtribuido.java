package tcc.sgmeabackend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoCriadoDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.FuncionarioDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.GestorDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.TecnicoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoSerializer;
import tcc.sgmeabackend.model.jackson.serializer.FuncionarioSerializer;
import tcc.sgmeabackend.model.jackson.serializer.GestorSerializer;
import tcc.sgmeabackend.model.jackson.serializer.TecnicoSerializer;

import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chamado_atribuido")
public class ChamadoAtribuido implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    @OneToOne
    @JoinColumn(name = "chamado_criado_id", unique = true)
    @JsonSerialize(using = ChamadoSerializer.class)
    @JsonDeserialize(using = ChamadoCriadoDesserializer.class)
    private ChamadoCriado chamadoCriado;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tecnico_id")
    @JsonSerialize(using = TecnicoSerializer.class)
    @JsonDeserialize(using = TecnicoDesserializer.class)
    private Tecnico tecnico;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "gestor_id")
    @JsonSerialize(using = GestorSerializer.class)
    @JsonDeserialize(using = GestorDesserializer.class)
    private Gestor gestor;

    private Prioridade prioridade;
}