package tcc.sgmeabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import tcc.sgmeabackend.service.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements Rest<T> {

    protected abstract JpaRepository<T, String> getRepository();

    @Override
    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    public Page<T> findAllPagination(PageRequest pageRequest) {
        return this.getRepository().findAll(pageRequest);
    }

    @Override
    public Optional<T> findById(String id) {
        return this.getRepository().findById(id);
    }

    @Override
    public T create(T resource) {
        return this.getRepository().save(resource);
    }

    @Override
    public T update(String id, T resource) {
        Optional<T> existingResource = this.getRepository().findById(id);
        if (existingResource.isPresent()) {
            return this.getRepository().save(resource);
        } else {
            throw new ResourceNotFoundException("Resource with id " + id + " not found");
        }
    }

    @Override
    public void delete(String id) {
        Optional<T> existingResource = this.getRepository().findById(id);
        if (existingResource.isPresent()) {
            this.getRepository().deleteById(id);
        } else {
            throw new ResourceNotFoundException("Resource with id " + id + " not found");
        }
    }
}
