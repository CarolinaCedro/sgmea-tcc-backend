package tcc.sgmeabackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.ChamadoAtribuido;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.dtos.ChamadoConsolidado;
import tcc.sgmeabackend.notifications.model.EmailDto;
import tcc.sgmeabackend.repository.ChamadoCriadoRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final ChamadoCriadoRepository chamadoCriadoRepository;

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


    public void chamadoCriado(String funcionarioEmail, String gestorEmail, String descricao, String gestorNome, String funcionarioNome) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            LocalDate dataAbertura = LocalDate.now();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataAbertura.format(formatter);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Chamado Criado");

            // Adiciona múltiplos destinatários
            helper.addTo(gestorEmail);
            helper.addTo(funcionarioEmail);

            String template = chamdoCriadoTemplateEmail();
            template = template.replace("#{Descrição}", descricao);
            template = template.replace("#{funcionario}", funcionarioNome);
            template = template.replace(" #{data}", dataFormatada);
            helper.setText(template, true);

            javaMailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chamadoAtribuido(String funcionarioEmail, String gestorEmail, ChamadoAtribuido chamadoAtribuido) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            LocalDate dataAbertura = LocalDate.now();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataAbertura.format(formatter);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Chamado Atribuido");

            // Adiciona múltiplos destinatários
            helper.addTo(gestorEmail);
            helper.addTo(funcionarioEmail);
            helper.addTo(chamadoAtribuido.getTecnico().getEmail());

            Optional<ChamadoCriado> chamadoCriado = this.chamadoCriadoRepository.findById(chamadoAtribuido.getChamadoCriado().getId());
            if (chamadoCriado.isPresent()) {
                ChamadoCriado chamado = chamadoCriado.get();
                String template = pathForTemplateEmail("chamado-atribuido-response-notify.html");
                
                template = template.replace("#{nome_funcionario}", chamado.getFuncionario().getNome());
                template = template.replace("#{prioridade_chamado}", chamadoAtribuido.getPrioridade().toString());
                template = template.replace("#{nome_tecnico_atribuido}", chamadoAtribuido.getTecnico().getNome());
                helper.setText(template, true);

                javaMailSender.send(message);
            }


        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void consolidacaoChamado(String funcionarioEmail, String gestorEmail, ChamadoConsolidado chamadoConsolidado) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            LocalDate dataAbertura = LocalDate.now();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataAbertura.format(formatter);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Chamado Consolidado");

            // Adiciona múltiplos destinatários
            helper.addTo(gestorEmail);
            helper.addTo(funcionarioEmail);

            String template = pathForTemplateEmail("consolidacao-chamado.html");
            template = template.replace("#{ID}", chamadoConsolidado.getId());
            template = template.replace("#{descricao_chamado}", chamadoConsolidado.getObservacoes());
            template = template.replace("#{prioridade_chamado}", chamadoConsolidado.getPrioridade().toString());
            template = template.replace(" #{status_chamado}", chamadoConsolidado.getStatus().toString());
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

    public String pathForTemplateEmail(String template) throws IOException {
        ClassPathResource resource = new ClassPathResource(template);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}