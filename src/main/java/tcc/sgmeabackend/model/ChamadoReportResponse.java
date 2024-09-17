package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;

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
