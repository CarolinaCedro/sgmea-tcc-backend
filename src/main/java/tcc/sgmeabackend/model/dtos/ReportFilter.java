package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportFilter {

    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private String nomeEquipamento;


}
