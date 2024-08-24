package tcc.sgmeabackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tcc.sgmeabackend.model.MetadataPageable;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.enums.Operator;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.exceptions.SgmeaException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RestController<T, E> {

    ResponseEntity<PageableResource<E>> list(HttpServletResponse response, @RequestParam Map<String, String> allRequestParams);

    ResponseEntity<Optional<E>> findById(String id);

    ResponseEntity<E> create(T resource);

    ResponseEntity<E> update(String id, T resource);

    void delete(String id);


    default PageableResource toPageableResource(final AbstractService service, final HttpServletResponse response, final Map<String, String> params) {
        //validando os parametros passados
        final Map<String, String> allRequestParams = validPageable(params);
        final Long SKIP = Long.valueOf(allRequestParams.get(Operator.SKIP.toString()).toString());
        final Long LIMIT = Long.valueOf(allRequestParams.get(Operator.LIMIT.toString()).toString());


        final List records = service.findAll();
        return new PageableResource(records);
    }


    default Map<String, String> validPageable(final Map<String, String> allRequestParams) {
        final SgmeaException ex = new SgmeaException();
        Map<String, String> map = allRequestParams == null ? new LinkedHashMap<>() : new LinkedHashMap<>(allRequestParams);
        if (map.containsKey(Operator.LIMIT.toString())) {
            Integer limit = null;
            try {
                limit = Integer.valueOf(map.get(Operator.LIMIT.toString()));
                if (limit > this.getSgmeaRecords()) {
                    ex.addDetails(Operator.LIMIT.toString(), "o limite informado foi (" + limit + ") mas o maxímo é(" + this.getSgmeaRecords() + ")");
                }
            } catch (NumberFormatException nex) {
                ex.addDetails(Operator.LIMIT.toString(), "deve conter um numero com o tamanho maximo de " + this.getSgmeaRecords());
            }
        } else {
            map.put(Operator.LIMIT.toString(), String.valueOf(this.getSgmeaRecords()));
        }

        if (map.containsKey(Operator.SKIP.toString())) {
            Integer page = null;
            try {
                page = Integer.valueOf(map.get(Operator.SKIP.toString()));
                if (page < 0) {
                    ex.addDetails(Operator.SKIP.toString(), "a pagina informada foi (" + page + ") mas a deve ter o tamanho minimo de (0)");
                }
            } catch (final NumberFormatException nex) {
                ex.addDetails(Operator.SKIP.toString(), "deve conter um numero com o tamanho minimo de 0");
            }
        } else {
            map.put(Operator.SKIP.toString(), "0");
        }

        if (ex.containsDetais()) {
            throw ex;
        }
        return map;
    }

    default int getSgmeaRecords() {
        return 100;
    }


}
