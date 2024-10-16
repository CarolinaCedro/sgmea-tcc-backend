package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Tecnico;

import java.util.List;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, String> {
    List<Tecnico> findByNomeContainingIgnoreCase(String nome);

    Tecnico findByEmail(String emailTecnicoLogado);
}
