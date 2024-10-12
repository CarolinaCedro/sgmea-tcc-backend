package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.Equipamento;

import java.io.IOException;

public class EquipamentoSerializer extends JsonSerializer<Equipamento> {

    @Override
    public void serialize(final Equipamento gestor, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo gestor como uma string com o ID, não como um objeto
        if (gestor != null && gestor.getId() != null) {
            jsonGenerator.writeString(gestor.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
