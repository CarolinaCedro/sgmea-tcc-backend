package tcc.sgmeabackend.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Gestor;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.jackson.desserializer.EquipamentoDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.FuncionarioDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.GestorDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.TecnicoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.EquipamentoSerializer;
import tcc.sgmeabackend.model.jackson.serializer.FuncionarioSerializer;
import tcc.sgmeabackend.model.jackson.serializer.GestorSerializer;
import tcc.sgmeabackend.model.jackson.serializer.TecnicoSerializer;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoConsolidado {

    private String id;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private Prioridade prioridade;
    private String status;

    @JsonSerialize(using = EquipamentoSerializer.class)
    @JsonDeserialize(using = EquipamentoDesserializer.class)
    private Equipamento equipamento;

    private String titulo;
    private String observacaoConsolidacao;
    private String observacoes;

    @JsonSerialize(using = FuncionarioSerializer.class)
    @JsonDeserialize(using = FuncionarioDesserializer.class)
    private Funcionario funcionario;

    @JsonSerialize(using = GestorSerializer.class)
    @JsonDeserialize(using = GestorDesserializer.class)
    private Gestor gestor;

    @JsonSerialize(using = TecnicoSerializer.class)
    @JsonDeserialize(using = TecnicoDesserializer.class)
    private Tecnico tecnico;


    public ChamadoConsolidado(String funcionarioNaoEncontrado) {
        throw new RuntimeException(funcionarioNaoEncontrado);
    }

}
