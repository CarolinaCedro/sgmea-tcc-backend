package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Equipamento;

public class EquipamentoDesserializer extends AbstractDesserializer<Equipamento> {


    @Override
    protected Equipamento newInstance(final String id) {
        Equipamento equipamento = new Equipamento();
        equipamento.setId(id);
        return equipamento;
    }
}
