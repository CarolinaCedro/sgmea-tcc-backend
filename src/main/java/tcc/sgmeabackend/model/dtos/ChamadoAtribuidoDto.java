package tcc.sgmeabackend.model.dtos;

import tcc.sgmeabackend.model.enums.Prioridade;

public record ChamadoAtribuidoDto(String chamadoId, String tecnicoId, String gestorId, Prioridade prioridade) {
}
