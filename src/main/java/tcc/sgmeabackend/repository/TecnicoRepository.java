package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Tecnico;
import tcc.sgmeabackend.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, String> {
    List<Tecnico> findByNomeContainingIgnoreCase(String nome);



    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);

}
