package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Tecnico;

import java.io.IOException;

public class TecnicoSerializer extends JsonSerializer<Tecnico> {

    @Override
    public void serialize(final Tecnico tecnico, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo tecnico como uma string com o ID, não como um objeto
        if (tecnico != null && tecnico.getId() != null) {
            jsonGenerator.writeString(tecnico.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
