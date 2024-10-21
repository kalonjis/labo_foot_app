package com.labospring.LaboFootApp.pl.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//On va utiliser un front
@Controller
public class PasswordFormController {


    @GetMapping("/reset-password-form")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "forms/ResetPassword"; // retourne la vue Thymeleaf appelée "reset-password.html"
    }
}
