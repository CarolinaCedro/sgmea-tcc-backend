package tcc.sgmeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.sgmeabackend.model.Departamento;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
    List<Departamento> findByNomeContainingIgnoreCase(String nome);

}
