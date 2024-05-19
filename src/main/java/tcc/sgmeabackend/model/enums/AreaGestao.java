package tcc.sgmeabackend.model.enums;

public enum AreaGestao {
    ACADEMICO("Acadêmico"),
    ADMINISTRATIVO("Administrativo"),
    FINANCEIRO("Financeiro"),
    BIBLIOTECA("Biblioteca"),
    INFRAESTRUTURA("Infraestrutura"),
    TI("Tecnologia da Informação"),
    MARKETING("Marketing"),
    RELACOES_ESTUDANTIS("Relações Estudantis"),
    OUTROS("Outros");

    private final String descricao;

    AreaGestao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

