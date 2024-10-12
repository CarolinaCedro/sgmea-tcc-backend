package tcc.sgmeabackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByNome(String nome);

    boolean findByCpf(String cpf);

    User findByEmail(String email);

    User findByResetToken(String token);

}
