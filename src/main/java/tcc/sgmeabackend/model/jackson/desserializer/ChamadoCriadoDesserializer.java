package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.ChamadoCriado;

public class ChamadoCriadoDesserializer extends AbstractDesserializer<ChamadoCriado> {


    @Override
    protected ChamadoCriado newInstance(final String id) {
        ChamadoCriado chamado = new ChamadoCriado();
        chamado.setId(id);
        return chamado;
    }
}
