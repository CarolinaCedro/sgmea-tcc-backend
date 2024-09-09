package tcc.sgmeabackend.model.jackson.desserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.io.IOException;


@AllArgsConstructor
public abstract class AbstractDesserializer<T> extends JsonDeserializer<T> {


    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        if (node == null || node.isNull()) {
            return null;
        }

        return newInstance(node.asText());
    }

    protected abstract T newInstance(String var);

}
