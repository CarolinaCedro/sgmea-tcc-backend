package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.model.dtos.ResetPasswordRequest;
import tcc.sgmeabackend.model.dtos.UserForgotPasswordRequestDto;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.UserServiceImpl;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/sgmea/v1/users")
public class UserController extends AbstractController<User, User> {

    private final UserServiceImpl service;
    private final UserRepository userRepository;

    public UserController(UserServiceImpl service, ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper);
        this.service = service;
        this.userRepository = userRepository;
    }

    @Override
    protected Class<User> getDtoClass() {
        return User.class;
    }

    @Override
    protected AbstractService<User> getService() {
        return service;
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserForgotPasswordRequestDto forgotPasswordRequest) {
        this.service.processForgotPassword(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok("Password recovery instructions sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
        }

        // Verifica se o token não está expirado
        if (user.getResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expirado.");
        }

        // Atualiza a senha do usuário
        user.setSenha(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiryDate(null);
        userRepository.save(user);

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }


}
