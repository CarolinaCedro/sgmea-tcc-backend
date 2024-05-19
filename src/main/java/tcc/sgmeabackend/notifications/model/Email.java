package tcc.sgmeabackend.notifications.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import tcc.sgmeabackend.notifications.model.enums.StatusEmail;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    private UUID emailId;
    private String referenteProprietario;
    private String remetenteEmail;
    private String destinatarioEmail;
    private String assunto;
    @Column(columnDefinition = "TEXT")
    private String texto;
    private LocalDateTime dataEnvioEmail;
    private StatusEmail statusEmail;
}
