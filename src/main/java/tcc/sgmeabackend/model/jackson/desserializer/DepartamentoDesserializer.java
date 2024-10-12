package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Departamento;

public class DepartamentoDesserializer extends AbstractDesserializer<Departamento> {

    @Override
    protected Departamento newInstance(final String id) {
        Departamento departamento = new Departamento();
        departamento.setId(id);
        return departamento;
    }
}
