package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Gestor;

public class GestorDesserializer extends AbstractDesserializer<Gestor> {


    @Override
    protected Gestor newInstance(final String id) {
        Gestor gestor = new Gestor();
        gestor.setId(id);
        return gestor;
    }
}
