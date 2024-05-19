package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoCriado;

@Repository
public interface ChamadoCriadoRepository extends JpaRepository<ChamadoCriado, String> {
}
