package tcc.sgmeabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Rest<T> {


    List<T> findAll();

    T findById(String id);

    T create(T resource);

    T update(String id, T resource);

    void delete(String id);


    Page<T> findAll(Pageable pageable);


}
