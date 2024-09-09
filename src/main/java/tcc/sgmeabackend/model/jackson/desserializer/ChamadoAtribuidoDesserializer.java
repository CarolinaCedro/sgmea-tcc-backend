package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.ChamadoAtribuido;

public class ChamadoAtribuidoDesserializer extends AbstractDesserializer<ChamadoAtribuido> {


    @Override
    protected ChamadoAtribuido newInstance(final String id) {
        ChamadoAtribuido chamado = new ChamadoAtribuido();
        chamado.setId(id);
        return chamado;
    }
}
