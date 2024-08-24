package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponse {
    private String id;

    private String nome;

    private String descricao;
}
