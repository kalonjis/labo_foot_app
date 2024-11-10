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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.labospring.LaboFootApp.il.props.LaboFootProps.BACK_URL;


@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    public final UserService userService;
    public final PasswordResetTokenServiceImpl passwordResetTokenService;
    public final MailerService mailerService;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String token, @Valid @RequestBody PasswordResetForm form) {
        PasswordResetToken passwordToken = passwordResetTokenService.getOne(token);
        Map<String, String> response = new HashMap<>();

        if (passwordToken == null) {
            response.put("error", "Invalid token");
            return ResponseEntity.badRequest().body(response);
        }

        // Vérifier si le token a expiré
        if (passwordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String requestNewTokenUrl = BACK_URL + "/request-passwordtoken?token=" + token;
            String message = "Link has expired. Please request a new one at the following link: ";
            response.put("error", message);
            response.put("url", requestNewTokenUrl);
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/json")
                    .body(response);
        }

        userService.resetPassword(passwordToken.getUser().getId(), form.toBusiness());

        response.put("message", "Thank you. Your password has been successfully modified. You can now use it to connect to your favorite app.");
        return ResponseEntity.ok(response);
    }



    @GetMapping("/request-passwordtoken")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Map<String, String>> requestNewToken(@RequestParam String token) {
        PasswordResetToken passwordToken = passwordResetTokenService.getOne(token);
        Map<String, String> response = new HashMap<>();

        // Si le token est invalide
        if (passwordToken == null) {
            response.put("error", "Invalid token");
            return ResponseEntity.badRequest().body(response);
        }

        String newToken = passwordResetTokenService.generateNewToken(token, PasswordResetToken.class, 600L).getToken();
        mailerService.sendPasswordResetEmail(newToken);

        response.put("message", "A new confirmation email has been sent.");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/request-password")
    public ResponseEntity<Map<String, String>> requestPassword(@Valid @RequestBody UserSearchForm form) {
        User userCriteria = new User();
        userCriteria.setEmail(form.email());

        List<User> users = userService.getByCriteria(userCriteria);

        Map<String, String> response = new HashMap<>();
        if (users.isEmpty()) {
            response.put("error", "Sorry, but there are no users matching the information you entered.");
            return ResponseEntity.badRequest().body(response);
        }

        if (users.size() > 1) {
            response.put("error", "It seems that the name and email address you have entered do not correspond to the same user. Make sure they match, or choose one of the two");
            return ResponseEntity.badRequest().body(response);
        }

        PasswordResetToken token = passwordResetTokenService.createToken(users.getFirst(), PasswordResetToken.class, 6L);
        mailerService.sendPasswordResetEmail(token.getToken());

        response.put("message", "Check your inbox. If your e-mail address matches our database, you will receive an e-mail asking you to reset your password.");
        return ResponseEntity.ok(response);
    }

}
