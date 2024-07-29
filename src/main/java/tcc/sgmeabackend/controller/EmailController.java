package tcc.sgmeabackend.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.notifications.model.EmailDto;
import tcc.sgmeabackend.service.EmailService;

@RestController
@PermitAll
@RequestMapping("api/sgmea/v1/email")
public class EmailController {

    private final EmailService emailService;


    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok(emailService.justTest());
    }

    @PostMapping("/welcome")
    public void welcome(@RequestBody EmailDto emailDto) {
        emailService.boasVindas(emailDto);
    }

}
