package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

public final class PageableResource<T> implements Serializable {

    private final List<T> records;


    @JsonCreator
    public PageableResource(@JsonProperty("records") final List<T> records) {
        this.records = records;
    }

    public List<T> getRecords() {
        return records;
    }


    @JsonIgnore
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(this.records);
    }
}
