package tcc.sgmeabackend.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractController<T, E> implements RestController<T, E> {


    protected AbstractController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected abstract AbstractService<T> getService();

    @Override
    @GetMapping
    public ResponseEntity<PageableResource<E>> list(HttpServletResponse response, Map<String, String> allRequestParams) {
        return ResponseEntity.ok(toPageableResource(this.getService(), response, allRequestParams));
    }

    @GetMapping("/pagination")
    public Page<T> getUsers(@RequestParam(value = "offset", required = false) Integer offset,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = 10;
        if (StringUtils.isEmpty(sortBy)) sortBy = "id";
        return this.getService().findAllPagination(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }

    @Override
    @GetMapping(value = "/ids")
    public ResponseEntity findByIds(@RequestParam(required = false, value = "ids") String[] ids) {
        final Set<T> value = this.getService().findByIds(ids);

        return ResponseEntity.ok(value);
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
    public ResponseEntity<E> update(@PathVariable String id, @RequestBody T resource) {
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
