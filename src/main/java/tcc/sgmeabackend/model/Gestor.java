package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tcc.sgmeabackend.model.enums.AreaGestao;

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
    private List<ChamadoAtribuido> chamadoAtribuidos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", fetch = FetchType.LAZY)
    private List<User> usuariosAlocados = new ArrayList<>();


}
