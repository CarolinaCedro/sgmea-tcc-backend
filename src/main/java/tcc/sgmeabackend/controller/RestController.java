package tcc.sgmeabackend.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RestController<T, E> {

    ResponseEntity<List<E>> findAll();

    ResponseEntity<Optional<E>> findById(String id);

    ResponseEntity<E> create(T resource);

    ResponseEntity<E> update(String id, T resource);

    void delete(String id);
}
