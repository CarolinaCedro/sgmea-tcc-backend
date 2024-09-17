package tcc.sgmeabackend.model.dtos;

import java.time.LocalDate;

public record ReportFilter(LocalDate dataAbertura, LocalDate dataFechamento, String nomeEquipamento) {
}
