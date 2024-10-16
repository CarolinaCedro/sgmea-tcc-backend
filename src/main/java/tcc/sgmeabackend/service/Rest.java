package tcc.sgmeabackend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Rest<T> {

    List<T> findAll();


    Optional<T> findById(String id);

    T create(T resource);

    T update(String id, T resource);

    void delete(String id);


    Set<T> findByIds(String[] ids);
}
