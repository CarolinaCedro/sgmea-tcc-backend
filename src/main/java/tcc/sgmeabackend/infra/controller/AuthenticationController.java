package tcc.sgmeabackend.infra.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.model.dtos.AuthenticationDto;
import tcc.sgmeabackend.model.dtos.RegisterDto;
import tcc.sgmeabackend.repository.PessoaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/sgmea/v1/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;


    private final PessoaRepository pessoaRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, PessoaRepository pessoaRepository) {
        this.authenticationManager = authenticationManager;
        this.pessoaRepository = pessoaRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Pessoa>> getAuthentication() {
        return ResponseEntity.ok(this.pessoaRepository.findAll());
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto resource) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(resource.nome(), resource.password());

        System.out.println("como chega nome e senha");
        System.out.println("nome " + resource.nome());
        System.out.println("senha " + resource.password());


        System.out.println("depois que passa " + usernamePassword);

        var auth = authenticationManager.authenticate(usernamePassword).getPrincipal();
        return ResponseEntity.ok().build();

    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto resource) {
        if (this.pessoaRepository.findByNome(resource.nome()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(resource.senha());

        Pessoa newUser = new Pessoa(resource.nome(), encryptedPassword, resource.role());

        System.out.println("user" + newUser);

        this.pessoaRepository.save(newUser);

        return ResponseEntity.ok().build();

    }


}
