package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.dtos.ChamadoCriadoResponse;
import tcc.sgmeabackend.model.enums.Status;

import java.util.List;

@Repository
public interface ChamadoCriadoRepository extends JpaRepository<ChamadoCriado, String> {


    List<ChamadoCriado> findByStatusIn(List<Status> status);

    @Query("SELECT c FROM ChamadoCriado c JOIN FETCH c.equipamento e WHERE c.status IN :status ORDER BY c.dataAbertura ASC")
    List<ChamadoCriado> findByStatusInAndFetchEquipamento(Status status);

    List<ChamadoCriado> findAllByStatusNotAndAlocadoIsFalse(Status status);

}
