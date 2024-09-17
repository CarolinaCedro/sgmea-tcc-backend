package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoReportResponse {

    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    private Prioridade prioridade;
    private Status status;
    private boolean alocado;
    private String descricaoEquipamento;
    private String titulo;
    private String observacaoConsolidacao;
    private String observacoes;

}
