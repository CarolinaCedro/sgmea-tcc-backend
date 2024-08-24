package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GestorResponse {

    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String perfil;
    private String areaGestao;
}
