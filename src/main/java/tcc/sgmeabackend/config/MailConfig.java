package tcc.sgmeabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    //spring.mail.host=smtp.gmail.com
    //spring.mail.port=587
    //spring.mail.username=manobixagency@gmail.com
    //spring.mail.password=hwnt hvio sngi qtjn
    //spring.mail.properties.mail.smtp.auth=true
    //spring.mail.properties.mail.smtp.starttls.enable=true

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("manobixagency@gmail.com");
        mailSender.setPassword("hwnt hvio sngi qtjn");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
