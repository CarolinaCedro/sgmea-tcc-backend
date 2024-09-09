package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.Especialidade;

import java.io.IOException;

public class EspecialidadeSerializer extends JsonSerializer<Especialidade> {

    @Override
    public void serialize(final Especialidade gestor, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo gestor como uma string com o ID, não como um objeto
        if (gestor != null && gestor.getId() != null) {
            jsonGenerator.writeString(gestor.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
