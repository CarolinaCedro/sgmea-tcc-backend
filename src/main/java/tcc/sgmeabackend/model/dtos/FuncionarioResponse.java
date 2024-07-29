package tcc.sgmeabackend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponse {

    private String id;
    private String nome;
    private String cpf;
    private String email;
    private GestorResponse gestor;
    private String perfil;
    private DepartamentoResponse departamento;
    private String funcao;
}
