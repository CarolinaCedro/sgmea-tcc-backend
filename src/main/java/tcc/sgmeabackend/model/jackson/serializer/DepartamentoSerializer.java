package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.Departamento;

import java.io.IOException;

public class DepartamentoSerializer extends JsonSerializer<Departamento> {

    @Override
    public void serialize(final Departamento departamento, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Serializa as propriedades individuais de Departamento
        if (departamento.getId() != null) {
            jsonGenerator.writeStringField("id", departamento.getId());  // Exemplo de propriedade id
        }


        jsonGenerator.writeEndObject();
    }
}
