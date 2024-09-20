package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;


@Entity
@Data
public class Equipamento {


    @Id
    @UuidGenerator
    private String id;
    private String nome;
    private String descricao;
    private String fabricante;
    private String modelo;
    private boolean emUso;

    @JsonIgnore
    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChamadoCriado> chamadosCriados;


    public Equipamento(String id, String nome, String descricao, String fabricante, String modelo, boolean emUso) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.emUso = emUso;
    }

    public Equipamento() {
    }
}



