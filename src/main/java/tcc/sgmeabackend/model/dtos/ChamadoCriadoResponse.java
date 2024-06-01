package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoCriadoResponse {

    private String id;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private String prioridade;
    private String status;
    private EquipamentoChamadoDto equipamento;
    private String titulo;
    private String observacaoConsolidacao;
    private String observacoes;
    private FuncionarioChamadoDto funcionario;
}
