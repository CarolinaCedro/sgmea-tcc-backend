package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;


@Entity
@Data
public class Equipamento {


    @Id
    @UuidGenerator
    private String id;
    @NotNull(message = "O campo nome é requerido")
    private String nome;
    private String descricao;
    @NotNull(message = "O campo patrimonio é requerido")
    private String patrimonio;
    @NotNull(message = "O campo nome do fabricante é requerido")
    private String fabricante;
    private String modelo;
    private boolean emUso;

    @JsonIgnore
    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChamadoCriado> chamadosCriados;


    public Equipamento(String id, String nome, String descricao, String patrimonio, String fabricante, String modelo, boolean emUso, List<ChamadoCriado> chamadosCriados) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.patrimonio = patrimonio;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.emUso = emUso;
        this.chamadosCriados = chamadosCriados;
    }

    public Equipamento() {
    }
}



