package tcc.sgmeabackend.notifications.model;

public record EmailDto(
        String destinatario,
        String assunto,
        String mensagem
) {
}
