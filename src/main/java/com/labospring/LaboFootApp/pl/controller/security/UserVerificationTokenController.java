package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.UserVerificationTokenService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserVerificationTokenController {

    private final UserVerificationTokenService userVerificationTokenService;
    private final UserService userService;

    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        // Récupérer le token de vérification
        UserVerificationToken userToken = userVerificationTokenService.getOne(token);
        System.out.println("usertoken = :" + userToken.getUser().getEmail());

        // Si le token est invalide
        if (userToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        // Vérifier si le token a expiré
        if (userToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired");
        }

        // Récupérer l'utilisateur correspondant au token
        User user = userToken.getUser();

        // Activer le compte de l'utilisateur
        userService.enableUser(user);

        // Réponse de succès avec message de confirmation
        return ResponseEntity.ok("Your account has been successfully activated.");
    }
}
