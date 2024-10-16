package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.Departamento;
import tcc.sgmeabackend.model.Gestor;

import java.io.IOException;

public class GestorSerializer extends JsonSerializer<Gestor> {

    @Override
    public void serialize(final Gestor gestor, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo gestor como uma string com o ID, não como um objeto
        if (gestor != null && gestor.getId() != null) {
            jsonGenerator.writeString(gestor.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
