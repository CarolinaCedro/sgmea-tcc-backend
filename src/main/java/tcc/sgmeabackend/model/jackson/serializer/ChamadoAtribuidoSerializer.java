package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.ChamadoAtribuido;

import java.io.IOException;

public class ChamadoAtribuidoSerializer extends JsonSerializer<ChamadoAtribuido> {

    @Override
    public void serialize(final ChamadoAtribuido chamado, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo chamado como uma string com o ID, não como um objeto
        if (chamado != null && chamado.getId() != null) {
            jsonGenerator.writeString(chamado.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
