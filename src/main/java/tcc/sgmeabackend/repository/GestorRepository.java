package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Gestor;

import java.util.List;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, String> {
    List<Gestor> findByNomeContainingIgnoreCase(String nome);

}

