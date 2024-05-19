package tcc.sgmeabackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipamento {


    @Id
    @UuidGenerator
    private String id;
    private String nome;
    private String descricao;
    private String fabricante;
    private String modelo;
    private boolean emUso;


}



