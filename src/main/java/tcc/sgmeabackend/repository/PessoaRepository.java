package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.model.Tecnico;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    UserDetails findByNome(String nome);
}
