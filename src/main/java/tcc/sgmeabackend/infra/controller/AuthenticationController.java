package tcc.sgmeabackend.infra.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.repository.PessoaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/sgmea/v1/auth")
public class AuthenticationController {

    protected static final Logger logger = LogManager.getLogger();


    @Autowired
    private  AuthenticationManager authenticationManager;


    private final PessoaRepository pessoaRepository;

    public AuthenticationController( PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Pessoa>> getAuthentication() {
        return ResponseEntity.ok(this.pessoaRepository.findAll());
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Pessoa resource) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(resource.getUsername(),resource.getPassword());



        logger.info("Tentativa de login do usuário: {}", resource.getNome());
        logger.info("Tentativa de login da senha: {}", resource.getSenha());



        logger.info("O que vem nesse usernamePassword: {}", usernamePassword.getPrincipal());
        logger.info("O que vem nesse credetians: {}", usernamePassword.getCredentials());
        logger.info("O que vem nesse object: {}", usernamePassword);


        try {

            var auth = authenticationManager.authenticate(usernamePassword);
            logger.info("Usuário autenticado com sucesso: {}", resource.getNome());
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            logger.error("Erro ao autenticar usuário: {}", e.getMessage());
            logger.error(e.getCause());
            logger.error(e.getLocalizedMessage());
            logger.error(e.getClass());
            logger.error(e.fillInStackTrace());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid Pessoa resource) {

        if (this.pessoaRepository.findByNome(resource.getNome()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(resource.getPassword());

        Pessoa newUser = new Pessoa(null,resource.getNome(), resource.getCpf(), resource.getEmail(),encryptedPassword, resource.getRole());

        System.out.println("user" + newUser);

        this.pessoaRepository.save(newUser);

        return ResponseEntity.ok().build();

    }


}
