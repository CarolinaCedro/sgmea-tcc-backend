package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoResponse {
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String gestor;
    private String perfil;
    private String especialidade;
    private boolean disponibilidade;
}
