package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.model.jackson.desserializer.EquipamentoDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.FuncionarioDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.EquipamentoSerializer;
import tcc.sgmeabackend.model.jackson.serializer.FuncionarioSerializer;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoCriado implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "O campo status é requerido")
    @NotEmpty
    private Status status;

    private boolean alocado;

    private Prioridade prioridade;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "equipamento_id")
    @JsonSerialize(using = EquipamentoSerializer.class)
    @JsonDeserialize(using = EquipamentoDesserializer.class)
    private Equipamento equipamento;

    @NotNull(message = "O campo titulo é requerido")
    @NotEmpty
    private String titulo;
    private String observacaoConsolidacao;
    private String observacoes;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "funcionario_id")
    @JsonSerialize(using = FuncionarioSerializer.class)
    @JsonDeserialize(using = FuncionarioDesserializer.class)
    private Funcionario funcionario;


    public ChamadoCriado(String funcionarioNaoEncontrado) {
        throw new RuntimeException(funcionarioNaoEncontrado);
    }

}
