package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.Gestor;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, String> {
}

