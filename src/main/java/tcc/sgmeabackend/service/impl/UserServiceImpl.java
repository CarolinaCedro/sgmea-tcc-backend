package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.UpdateUser;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.AbstractService;

import java.util.Optional;
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
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // Geração de token de recuperação de senha
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
        // Não há resposta de erro se o email não for encontrado
    }


    public Object updateUser(Optional<User> user, UpdateUser payloadUser) {
        if (user.isPresent()) {
            User currentUser = user.get();
            String oldPassword = currentUser.getPassword();
            String newPassword = payloadUser.getNovaSenha();
            String providedOldPassword = payloadUser.getOldSenha();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


            if (passwordEncoder.matches(providedOldPassword, oldPassword)) {

                currentUser.setId(payloadUser.getId());
                currentUser.setNome(payloadUser.getNome());
                currentUser.setCpf(payloadUser.getCpf());
                currentUser.setEmail(payloadUser.getEmail());
                currentUser.setSenha(passwordEncoder.encode(newPassword)); // Atualiza a senha

                return userRepository.save(currentUser);
            } else {
                throw new RuntimeException("Senha antiga incorreta.");
            }
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

}
