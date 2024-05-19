package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Equipamento;
import tcc.sgmeabackend.model.Funcionario;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, String> {
}
