package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.impl.UserVerificationTokenServiceImpl;
import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.labospring.LaboFootApp.il.props.LaboFootProps.BACK_URL;
import static com.labospring.LaboFootApp.il.props.LaboFootProps.FRONT_URL;

@RequiredArgsConstructor
@RestController
public class UserVerificationTokenController {

    private final UserVerificationTokenServiceImpl userVerificationTokenService;
    private final UserService userService;
    private final MailerService mailerService;

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Map<String, String>> confirmAccount(@RequestParam String token) {
        // Récupérer le token de vérification
        UserVerificationToken userToken = userVerificationTokenService.getOne(token);
        Map<String, String> response = new HashMap<>();

        // Si le token est invalide
        if (userToken == null) {
            response.put("type", "validity");
            response.put("message", "Invalid token");
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/json")
                    .body(response);
        }

        if (userToken.getUser().isEnabled()){
            response.put("type", "activation");
            response.put("message", "Account is already activated !");
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/json")
                    .body(response);
        }

        // Vérifier si le token a expiré
        if (userToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String requestNewTokenUrl = BACK_URL + "/request-confirmtoken?token=" + token;
            response.put("type", "expiration");
            response.put("message", "Link has expired. Please request a new one at the following link: ");
            response.put("url", requestNewTokenUrl);
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/json")
                    .body(response);
        }

        // Récupérer l'utilisateur correspondant au token
        User user = userToken.getUser();

        // Activer le compte de l'utilisateur
        userService.enableUser(user);

        mailerService.sendWelcomeEmail(user);

        // Réponse de succès avec message de confirmation
        response.put("message", "Thank you. Your account has been successfully activated. You can now use it to connect to your favorite app.");
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(response);
    }


    @GetMapping("/request-confirmtoken")
    public ResponseEntity<Map<String, String>> requestNewToken(@RequestParam String token){
        UserVerificationToken verificationToken = userVerificationTokenService.getOne(token);

        Map<String, String> response = new HashMap<>();
        // Si le token est invalide
        if (verificationToken == null) {
            response.put("error", "Invalid token");
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/json")
                    .body(response);
        }

        String newToken = userVerificationTokenService.generateNewToken(token, UserVerificationToken.class, 60L).getToken();
        mailerService.sendNewConfirmation(newToken);

        // Réponse de succès avec message de confirmation
        response.put("message", "A new confirmation email has been sent.");
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(response);
    }
}
