package tcc.sgmeabackend.model.jackson.desserializer;

import tcc.sgmeabackend.model.Funcionario;

public class FuncionarioDesserializer extends AbstractDesserializer<Funcionario> {


    @Override
    protected Funcionario newInstance(final String id) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return funcionario;
    }
}
