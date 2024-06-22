package tcc.sgmeabackend.notifications.model;

public record EmailDto(
        String emailDestinatario,
        String nome,
        String assunto,
        String mensagem
) {
}
