package tcc.sgmeabackend.model.jackson.desserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import tcc.sgmeabackend.model.Gestor;

import java.io.IOException;

public class GestorDesserializer extends AbstractDesserializer<Gestor> {


    @Override
    public Gestor deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        String id = parser.getText();
        Gestor gestor = new Gestor();
        gestor.setId(id);
        return gestor;
    }

    @Override
    protected Gestor newInstance(final String id) {
        Gestor gestor = new Gestor();
        gestor.setId(id);
        return gestor;
    }
}
