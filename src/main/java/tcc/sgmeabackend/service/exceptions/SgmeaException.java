package tcc.sgmeabackend.service.exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SgmeaException extends RuntimeException {

    protected HttpStatus status;
    protected String message;
    protected String objectName;
    protected Map<String, Object> details = new HashMap<>();
    protected Map<String, List<String>> headers = new HashMap<>();
    protected String field;


    public SgmeaException(){
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "ERROR *-*";
        this.objectName = "unknow :(";
        this.field = null;
    }



    public SgmeaException addDetails(final String key, final Object value) {
        this.details.put(key, value);
        return this;
    }


    public boolean containsDetais() {
        return !this.details.isEmpty();
    }

}
