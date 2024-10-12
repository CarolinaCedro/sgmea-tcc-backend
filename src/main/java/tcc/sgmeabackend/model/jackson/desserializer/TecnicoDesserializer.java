package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Tecnico;

public class TecnicoDesserializer extends AbstractDesserializer<Tecnico> {


    @Override
    protected Tecnico newInstance(final String id) {
        Tecnico tecnico = new Tecnico();
        tecnico.setId(id);
        return tecnico;
    }
}
