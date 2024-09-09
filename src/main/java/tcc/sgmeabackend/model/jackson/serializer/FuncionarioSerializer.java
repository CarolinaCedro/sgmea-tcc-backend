package tcc.sgmeabackend.model.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tcc.sgmeabackend.model.Especialidade;
import tcc.sgmeabackend.model.Funcionario;

import java.io.IOException;

public class FuncionarioSerializer extends JsonSerializer<Funcionario> {

    @Override
    public void serialize(final Funcionario funcionario, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        // Serializando o campo funcionario como uma string com o ID, não como um objeto
        if (funcionario != null && funcionario.getId() != null) {
            jsonGenerator.writeString(funcionario.getId());
        } else {
            jsonGenerator.writeNull();  // Se o gestor for nulo, escreve "null"
        }
    }
}
