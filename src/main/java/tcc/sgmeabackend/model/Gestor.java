package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.enums.AreaGestao;
import tcc.sgmeabackend.model.jackson.desserializer.ChamadoAtribuidoDesserializer;
import tcc.sgmeabackend.model.jackson.serializer.ChamadoAtribuidoSerializer;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gestor extends User {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private AreaGestao areaGestao;

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", fetch = FetchType.EAGER)
    @JsonSerialize(using = ChamadoAtribuidoSerializer.class)
    @JsonDeserialize(using = ChamadoAtribuidoDesserializer.class)
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", fetch = FetchType.LAZY)
    private List<User> usuariosAlocados = new ArrayList<>();


}
