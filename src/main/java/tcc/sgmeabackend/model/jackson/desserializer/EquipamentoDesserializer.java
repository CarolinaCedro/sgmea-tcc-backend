package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Equipamento;

public class EquipamentoDesserializer extends AbstractDesserializer<Equipamento> {


    @Override
    protected Equipamento newInstance(final String id) {
        Equipamento chamado = new Equipamento();
        chamado.setId(id);
        return chamado;
    }
}
