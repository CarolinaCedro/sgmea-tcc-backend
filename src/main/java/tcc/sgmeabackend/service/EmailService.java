package tcc.sgmeabackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.notifications.model.EmailDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void boasVindas(EmailDto emailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            System.out.println("email dto" + emailDto);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Dev Bueno");
            helper.setTo(emailDto.emailDestinatario());

            String template = carregaTemplateEmail();

            template = template.replace("#{nome}", emailDto.nome());
            helper.setText(template, true);
            javaMailSender.send(message);
        } catch (Exception exception) {
            System.out.println("Falha ao enviar o email" + exception.getMessage());
        }
    }


    public void chamadoCriado(String funcionarioEmail, String gestorEmail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Chamado Criado");

            // Adiciona múltiplos destinatários
            helper.addTo(gestorEmail);
            helper.addTo(funcionarioEmail);

            String template = chamdoCriadoTemplateEmail();
            helper.setText(template, true);

            javaMailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String justTest() {
        return "Teste";
    }

    public String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("template-email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public String chamdoCriadoTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("chamado-criado-email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}