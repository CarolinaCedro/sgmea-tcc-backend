package tcc.sgmeabackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ObjectUtils;
import tcc.sgmeabackend.service.exceptions.ResourceNotFoundException;

import java.util.*;

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
        System.out.println("_________________________");
        System.out.println("o resource para update " + existingResource);
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

    @Override
    public Set<T> findByIds(final String[] ids) {
        // Verifica se os IDs são nulos ou vazios
        if (ObjectUtils.isEmpty(ids)) {
            throw new RuntimeException("Informe pelo menos um id válido");
        }

        // Verifica se a quantidade de IDs excede o limite de 50
        if (ids.length > 50) {
            throw new RuntimeException("Quantidade máxima excedida");
        }

        // Converte o array de IDs em uma lista
        final List<String> idList = Arrays.asList(ids);

        // Busca os registros usando o repositório
        final List<T> records = this.getRepository().findAllById(idList);

        // Se nenhum registro for encontrado, retorna um conjunto vazio
        if (records == null || records.isEmpty()) {
            return Collections.emptySet();
        }

        // Converte a lista de registros em um Set para garantir unicidade
        return new HashSet<>(records);
    }


}
