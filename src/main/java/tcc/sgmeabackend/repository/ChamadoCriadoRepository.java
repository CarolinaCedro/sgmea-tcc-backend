package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChamadoCriadoRepository extends JpaRepository<ChamadoCriado, String> {


    List<ChamadoCriado> findByStatusIn(List<Status> status);


    @Query("SELECT c FROM ChamadoCriado c JOIN FETCH c.equipamento e " +
            "WHERE c.status = :status " +
            "AND (:dataAbertura IS NULL OR c.dataAbertura >= :dataAbertura) " +
            "AND (:dataFechamento IS NULL OR c.dataAbertura <= :dataFechamento) " +
            "AND (:nomeEquipamento IS NULL OR e.nome LIKE %:nomeEquipamento%) " +
            "ORDER BY c.dataAbertura ASC")
    List<ChamadoCriado> findByStatusAndOptionalFilters(
            @Param("status") Status status,
            @Param("dataAbertura") LocalDate dataAbertura,
            @Param("dataFechamento") LocalDate dataFechamento,
            @Param("nomeEquipamento") String nomeEquipamento);


    List<ChamadoCriado> findAllByStatusNotAndAlocadoIsFalse(Status status);

    List<ChamadoCriado> findByStatus(Status statusConcluido);


    List<ChamadoCriado> findAllByStatusNotInAndAlocadoFalse(List<Status> status);



}
