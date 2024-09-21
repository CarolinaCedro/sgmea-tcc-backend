package tcc.sgmeabackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String oldSenha;
    private String novaSenha;
    private String confirmSenha;

}
