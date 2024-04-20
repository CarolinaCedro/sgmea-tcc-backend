package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Chamado;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, String> {
}
