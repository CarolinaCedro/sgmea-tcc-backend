package tcc.sgmeabackend.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.service.AbstractService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractController<T, E> implements RestController<T, E> {

    private final ModelMapper modelMapper;

    protected AbstractController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected abstract AbstractService<T> getService();

    @Override
    @GetMapping
    public ResponseEntity<List<E>> findAll() {
        List<T> result = this.getService().findAll();
        List<E> response = result.stream()
                .map(entity -> modelMapper.map(entity, getDtoClass()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Optional<E>> findById(@PathVariable String id) {
        Optional<T> result = this.getService().findById(id);
        if (result.isPresent()) {
            E response = modelMapper.map(result.get(), getDtoClass());
            return ResponseEntity.ok(Optional.of(response));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<E> create(@Valid @RequestBody T resource) {
        T createdResource = this.getService().create(resource);
        E response = modelMapper.map(createdResource, getDtoClass());
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<E> update(@PathVariable String id, @Valid @RequestBody T resource) {
        T updatedResource = this.getService().update(id, resource);
        E response = modelMapper.map(updatedResource, getDtoClass());
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.getService().delete(id);
    }

    protected abstract Class<E> getDtoClass();
}
