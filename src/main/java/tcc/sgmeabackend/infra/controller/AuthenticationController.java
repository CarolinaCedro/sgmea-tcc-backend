package tcc.sgmeabackend.infra.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.infra.security.TokenService;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.Perfil;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.model.dtos.AuthenticationDto;
import tcc.sgmeabackend.model.dtos.LoginResponseDTO;
import tcc.sgmeabackend.model.dtos.RegisterDto;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.repository.UserRepository;

@RestController
@RequestMapping("/api/sgmea/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private GestorRepository gestorRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;


    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.nome(), data.senha());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data) {
        if (this.repository.findByNome(data.nome()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        User newUser = new User(null, data.nome(), data.cpf(), data.email(), encryptedPassword, data.role(), data.perfil());

        try {
            if (data.perfil().equals(Perfil.FUNCIONARIO)) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(newUser.getId());
                funcionario.setNome(newUser.getNome());
                funcionario.setEmail(newUser.getEmail());
                funcionario.setSenha(newUser.getSenha());
                funcionario.setGestor(newUser.getGestor());
                funcionario.setDepartamento(null);
                funcionario.setChamadoCriados(null);
                funcionario.setFuncao("Não foi informado");
                funcionario.setRole(newUser.getRole());
                funcionario.setPerfil(newUser.getPerfil());

                this.funcionarioRepository.save(funcionario);

                return ResponseEntity.ok().build();
            }

            this.repository.save(newUser);
//            this.emailService.enviarEmailTexto(data.email(), "Novo Cadastro", "Foi criado um novo usuario no sistema");
            return ResponseEntity.ok().build(); // Envio de e-mail bem-sucedido
        } catch (MailSendException e) {
            // Captura de exceção no envio do e-mail
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar o e-mail: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }


    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            return ResponseEntity.ok(currentUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}



