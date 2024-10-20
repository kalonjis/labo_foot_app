package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.impl.PasswordResetTokenServiceImpl;
import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.PasswordResetToken;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.user.UserSearchForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    public final UserService userService;
    public final PasswordResetTokenServiceImpl passwordResetTokenService;
    public final MailerService mailerService;

    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token){
        return ResponseEntity.ok("Hello");
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
