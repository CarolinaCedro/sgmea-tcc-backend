package tcc.sgmeabackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.PageableResource;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, String> {
    List<Equipamento> findByNomeContainingIgnoreCase(String nome);

    List<Equipamento> findAllByEmUsoTrue();
    List<Equipamento> findAllByEmUsoFalse();

}
