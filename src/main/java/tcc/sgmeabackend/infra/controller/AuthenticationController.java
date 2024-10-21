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
import tcc.sgmeabackend.model.*;
import tcc.sgmeabackend.model.dtos.AuthenticationDto;
import tcc.sgmeabackend.model.dtos.LoginResponseDTO;
import tcc.sgmeabackend.model.dtos.RegisterDto;
import tcc.sgmeabackend.repository.FuncionarioRepository;
import tcc.sgmeabackend.repository.GestorRepository;
import tcc.sgmeabackend.repository.TecnicoRepository;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.exceptions.CpfAlocadoException;
import tcc.sgmeabackend.service.exceptions.EmailAlocadoException;

import java.util.Optional;

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


    @GetMapping("/ping")
    public String ping() {
        return "Pong! A API está funcionando corretamente!";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto data) {
        Optional<User> existingUser = Optional.ofNullable(this.repository.findByEmail(data.email()));
        if (existingUser.isPresent()) {
            throw new EmailAlocadoException("email já está alocado a outro usúario !");
        }

        Optional<User> existingUserForCpf = this.repository.findByCpf(data.cpf());
        if (existingUserForCpf.isPresent()) {
            throw new CpfAlocadoException("cpf já está alocado a outro usúario !");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        User newUser = new User(null, data.nome(), data.cpf(), data.email(), encryptedPassword, data.role(), data.perfil());
        System.out.println("Usuário que vem para salvar: " + newUser);

        try {
            Gestor gestor = new Gestor();
            gestor.setId(newUser.getId());
            gestor.setNome(newUser.getNome());
            gestor.setEmail(newUser.getEmail());
            gestor.setCpf(newUser.getCpf());
            gestor.setSenha(newUser.getSenha());
            gestor.setAreaGestao(null);
            gestor.setUsuariosAlocados(null);
            gestor.setChamadoAtribuidos(null);
            gestor.setRole(newUser.getRole());
            gestor.setPerfil(newUser.getPerfil());
            this.gestorRepository.save(gestor);

//            this.repository.save(newUser);


            return ResponseEntity.ok().body(gestor);


        } catch (Exception e) {
            // Captura de qualquer outro erro inesperado
            e.printStackTrace();
            System.out.println("Erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar o usuário: " + e.getMessage());
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



