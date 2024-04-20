package tcc.sgmeabackend.controller;

import org.springframework.http.ResponseEntity;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;

public abstract class AbstractController<T> implements RestController<T> {


    protected abstract AbstractService<T> getService();


    @Override
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(this.getService().findAll());
    }

    @Override
    public ResponseEntity<Optional<T>> findById(String id) {

        Optional<T> result = this.getService().findById(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<T> create(T resource) {
        return ResponseEntity.ok(this.getService().create(resource));
    }

    @Override
    public ResponseEntity<T> update(String id, T resource) {
        return ResponseEntity.ok(this.getService().create(resource));
    }

    @Override
    public void delete(String id) {
        this.getService().delete(id);
    }

//    @Override
//    public ResponseEntity<Page<T>> findAll(Pageable pageable) {
//        return ResponseEntity.ok(this.getService().findAll(pageable.getPageFormat(1)));
//    }
}
