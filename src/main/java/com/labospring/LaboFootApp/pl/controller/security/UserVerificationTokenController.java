package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.UserVerificationTokenService;
import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserVerificationTokenController {

    private final UserVerificationTokenService userVerificationTokenService;
    private final UserService userService;
    private final MailerService mailerService;

    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        // Récupérer le token de vérification
        UserVerificationToken userToken = userVerificationTokenService.getOne(token);

        // Si le token est invalide
        if (userToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        // Vérifier si le token a expiré
        if (userToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String requestNewTokenUrl = "http://localhost:8080/requestNewToken?token=" + token;
            String message = "Link has expired. Please request a new one at the following link: " +
            "<br/> <a href=\"" + requestNewTokenUrl + "\">Request New Confirmation Email</a>";
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/html")
                    .body(message);
        }

        // Récupérer l'utilisateur correspondant au token
        User user = userToken.getUser();

        // Activer le compte de l'utilisateur
        userService.enableUser(user);

        // Réponse de succès avec message de confirmation
        return ResponseEntity.ok("Your account has been successfully activated.");
    }


    @GetMapping("/requestNewToken")
    public ResponseEntity<String> requestNewToken(@RequestParam String token){
        UserVerificationToken verificationToken = userVerificationTokenService.getOne(token);

        // Si le token est invalide
        if (verificationToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String newToken = userVerificationTokenService.generateNewVerificationToken(token).getToken();
        mailerService.sendNewConfirmation(newToken);

        return ResponseEntity.ok("A new confirmation email has been sent.");
    }
}
