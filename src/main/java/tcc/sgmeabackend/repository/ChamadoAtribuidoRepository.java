package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.enums.Status;

import java.util.List;

@Repository
public interface ChamadoAtribuidoRepository extends JpaRepository<ChamadoAtribuido, String> {


    @Query("SELECT c FROM ChamadoAtribuido c WHERE c.chamadoCriado.status <> 3 ")
    List<ChamadoAtribuido> findAllChamadosAlocadosSemConcluidos();

}

