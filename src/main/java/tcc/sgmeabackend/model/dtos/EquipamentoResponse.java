package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoResponse {
    private String id;
    private String nome;
    private String descricao;
    private String fabricante;
    private String modelo;
    private boolean emUso;
}
