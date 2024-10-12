package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.UpdateUser;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.model.dtos.ResetPasswordRequest;
import tcc.sgmeabackend.model.dtos.UserForgotPasswordRequestDto;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public void forgotPassword(@RequestBody UserForgotPasswordRequestDto forgotPasswordRequest) {
        this.service.processForgotPassword(forgotPasswordRequest.getEmail());
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken());

        if (user == null) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        // Verifica se o token não está expirado
        if (user.getResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado.");
        }

        // Atualiza a senha do usuário
        user.setSenha(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiryDate(null);
        userRepository.save(user);

    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUser payloadUser) {
        Optional<User> user = userRepository.findById(payloadUser.getId());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User não encontrado.");
        }
        return ResponseEntity.ok(this.service.updateUser(user, payloadUser));
    }


}
