package tcc.sgmeabackend.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    // Contar novas propostas
    @Query("SELECT COUNT(c) FROM ChamadoCriado c WHERE c.status = 1")
    int countPropostasNovas();


    // Contar chamados por status
    // Repositório - pesquisar usando o valor ordinal do enum

    int countByStatus(Status status);


    // Consulta para contar chamados por equipamento e ordenar pelo número de chamados
    @Query("SELECT c.equipamento.nome AS equipamento, COUNT(c) AS totalChamados " +
            "FROM ChamadoCriado c " +
            "WHERE c.status = :status " +
            "GROUP BY c.equipamento " +
            "ORDER BY totalChamados DESC")
    List<Map<String, Object>> findTopEquipamentosByChamados(@Param("status") Status status, Pageable pageable);

    @Query("SELECT COUNT(c) FROM ChamadoCriado c " +
            "WHERE c.dataAbertura BETWEEN :startOfWeek AND :endOfWeek AND c.status = :status")
    int countChamadosByWeek(@Param("startOfWeek") LocalDate startOfWeek,
                            @Param("endOfWeek") LocalDate endOfWeek,
                            @Param("status") Status status);




    List<ChamadoCriado> findAllByStatusNotInAndAlocadoFalse(List<Status> status);



    List<ChamadoCriado> findByTituloContainingIgnoreCase(String titulo);

     List<ChamadoCriado> findAllByTituloContainingAndStatusNotInAndAlocadoFalse(String titulo, List<Status> status);

     List<ChamadoCriado> findByTituloContainingAndStatusIn(String titulo, List<Status> statusEncerrados);



}
