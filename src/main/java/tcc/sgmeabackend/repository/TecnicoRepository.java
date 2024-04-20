package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, String> {
}
