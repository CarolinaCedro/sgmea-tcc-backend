package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.UUID;

@Service
public class UserServiceImpl extends AbstractService<User> {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;


    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }


    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    public void processForgotPassword(String email) {
        User user =  userRepository.findByEmail(email);
        if (user != null) {
            // Geração de token de recuperação de senha (pode ser um UUID, por exemplo)
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);

            // Enviando o email de recuperação
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Recuperação de Senha");
            mailMessage.setText("Para redefinir sua senha, clique no link abaixo:\n" +
                    "http://localhost:4200/reset-password?token=" + token);

            mailSender.send(mailMessage);
        }
    }
}
