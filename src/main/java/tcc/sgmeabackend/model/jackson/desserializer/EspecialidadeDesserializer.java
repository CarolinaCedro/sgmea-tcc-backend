package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Especialidade;

public class EspecialidadeDesserializer extends AbstractDesserializer<Especialidade> {


    @Override
    protected Especialidade newInstance(final String id) {
        Especialidade chamado = new Especialidade();
        chamado.setId(id);
        return chamado;
    }
}
