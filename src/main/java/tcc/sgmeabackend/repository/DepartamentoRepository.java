package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
}
