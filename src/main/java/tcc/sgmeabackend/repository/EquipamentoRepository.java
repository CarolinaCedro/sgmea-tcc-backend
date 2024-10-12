package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Equipamento;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, String> {
    List<Equipamento> findByNomeContainingIgnoreCase(String nome);

}
