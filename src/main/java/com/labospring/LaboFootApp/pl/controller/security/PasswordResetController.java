package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.impl.PasswordResetTokenServiceImpl;
import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.PasswordResetToken;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.user.PasswordResetForm;
import com.labospring.LaboFootApp.pl.models.user.UserSearchForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    public final UserService userService;
    public final PasswordResetTokenServiceImpl passwordResetTokenService;
    public final MailerService mailerService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @Valid @RequestBody PasswordResetForm form){
        PasswordResetToken passwordToken = passwordResetTokenService.getOne(token);

        if (passwordToken == null){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        // Vérifier si le token a expiré
        if (passwordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String requestNewTokenUrl = "http://localhost:8080/request-passwordtoken?token=" + token;
            String message = "Link has expired. Please request a new one at the following link: " +
                    "<br/> <a href=\"" + requestNewTokenUrl + "\">Request reset password email</a>";
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/html")
                    .body(message);
        }
        userService.resetPassword(passwordToken.getUser().getId(), form.toBusiness());

        return ResponseEntity.ok().body("Thank you.\n" +
                "your password has been successfully modified. You can now use it to connect to your favorite app. “login") ;



    }

    @GetMapping("/request-passwordtoken")
    public ResponseEntity<String> requestNewToken(@RequestParam String token){
        PasswordResetToken passwordToken = passwordResetTokenService.getOne(token);

        // Si le token est invalide
        if (passwordToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String newToken = passwordResetTokenService.generateNewToken(token, PasswordResetToken.class, 60L).getToken();
        mailerService.sendPasswordResetEmail(newToken);

        return ResponseEntity.ok("A new confirmation email has been sent.");
    }

    @PostMapping("/request-password")
    public ResponseEntity<String> requestPassword(@Valid @RequestBody UserSearchForm form){
        User userCriteria = new User();
        userCriteria.setUsername(form.username());
        userCriteria.setEmail(form.email());
        List<User> users = userService.getByCriteria(userCriteria);
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("Sorry, but there are no users matching the information you entered.");
        }
        if(users.size() > 1){
            return ResponseEntity.badRequest().body("It seems that the name and email address you have entered do not " +
                    "correspond to the same user. Make sure they match, or choose one of the two");
        }

        PasswordResetToken token = passwordResetTokenService.createToken(users.get(0), PasswordResetToken.class, 60L);
        mailerService.sendPasswordResetEmail(token.getToken());

        return ResponseEntity.ok("Check your inbox\n" +
                "If your e-mail address matches our database, you will receive an e-mail asking you to reset your password.");
    }

}
