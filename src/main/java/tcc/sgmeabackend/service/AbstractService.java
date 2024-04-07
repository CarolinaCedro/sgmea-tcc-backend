package tcc.sgmeabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements Rest<T> {

    abstract JpaRepository<T, String> getRepository();


    @Override
    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    @Override
    public T findById(String id) {
        Optional<T> optionalResource = getRepository().findById(id);
        return optionalResource.orElse(null);
    }

    @Override
    public T create(T resource) {
        return this.getRepository().save(resource);
    }

    @Override
    public T update(String id, T resource) {
        return this.getRepository().save(resource);
    }

    @Override
    public void delete(String id) {
        this.getRepository().deleteById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }
}
