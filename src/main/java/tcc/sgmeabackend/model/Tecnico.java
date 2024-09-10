package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoAtribuidoDesserializer;
import tcc.sgmeabackend.model.jackson.desserializer.EspecialidadeDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoAtribuidoSerializer;
import tcc.sgmeabackend.model.jackson.serializer.EquipamentoSerializer;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Entity
public class Tecnico extends User {

    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name = "tecnico_especialidade",
            joinColumns = @JoinColumn(name = "tecnico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
//    @JsonSerialize(using = EquipamentoSerializer.class)
//    @JsonDeserialize(using = EspecialidadeDesserializer.class)
    private List<Especialidade> especialidades = new ArrayList<>();

    private boolean disponibilidade;


    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.EAGER)
    @JsonSerialize(using = ChamadoAtribuidoSerializer.class)
    @JsonDeserialize(using = ChamadoAtribuidoDesserializer.class)
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public void consolidarChamado(ChamadoAtribuido chamadoAtribuido, String observacaoConsolidacao) {
//        ChamadoCriado chamadoCriado = chamadoAtribuido.getChamadoCriado();
//        chamadoCriado.finalizarChamado(observacaoConsolidacao);
        // Salvar alterações no banco de dados
    }
}
