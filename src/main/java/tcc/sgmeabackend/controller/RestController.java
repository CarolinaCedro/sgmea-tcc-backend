package tcc.sgmeabackend.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface RestController<T> {

    ResponseEntity<List<T>> findAll();

    ResponseEntity<Optional<T>> findById(String id);

    ResponseEntity<T> create(T resource);

    ResponseEntity<T> update(String id, T resource);

    void delete(String id);


//    ResponseEntity<Page<T>> findAll(Pageable pageable);
}
