package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.exceptions.BadEnabledStatusException;
import com.labospring.LaboFootApp.bll.exceptions.UserCredentialAlreadyTakenException;
import com.labospring.LaboFootApp.bll.security.AuthService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.il.utils.JwtUtils;
import com.labospring.LaboFootApp.pl.models.user.UserCreateForm;
import com.labospring.LaboFootApp.pl.models.user.UserLoginForm;
import com.labospring.LaboFootApp.pl.models.user.UserTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    /**
     * Handles the user registration process.
     * This endpoint is only accessible to anonymous users (i.e., users who are not logged in).
     *
     * @param form The form containing the user's registration information, validated by the @Valid annotation.
     * @return A ResponseEntity containing the generated UserTokenDTO if registration is successful.
     */
    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserCreateForm form) {
        Map<String, String> response = new HashMap<>();
        try {
            // Enregistrement de l'utilisateur
            User u = authService.register(form.toUser());
            //mapUserToken(u);

            // Message de succès
            response.put("message", "Thank you. You've been successfully registered. One last step: check your email and confirm your account");
            return ResponseEntity.ok(response);

        } catch (UserCredentialAlreadyTakenException e) {
            // Cas où le nom d'utilisateur ou l'email est déjà pris
            response.put("error", e.getMessage());
            return status(HttpStatus.CONFLICT).body(response);

        } catch (Exception e) {
            // Erreur générique
            response.put("error", "An error occurred during registration. Please try again later.");
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     * Handles the user login process.
     * This endpoint is only accessible to anonymous users (i.e., users who are not logged in).
     *
     * @param form The form containing the user's login information.
     * @return A ResponseEntity containing the generated UserTokenDTO if login is successful.
     */
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> login(@RequestBody UserLoginForm form) {
        try {
            User u = authService.login(form.username(), form.password());
            return ResponseEntity.ok(mapUserToken(u));
        } catch (BadEnabledStatusException e) {
            throw e;
        } catch (Exception e) {
            // Capture uniquement les autres exceptions génériques
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            //log.error("Unhandled exception during login", e);
            return ResponseEntity
                    .badRequest()
                    .header("Content-Type", "application/json")
                    .body(errorResponse);
        }
    }

    //@PreAuthorize("isAuthenticated()")
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//        // Le mécanisme de Spring Security se charge du logout via la configuration
//        return ResponseEntity.ok("Logout successful");
//    }


    /**
     * Maps a User entity to a UserTokenDTO, generating a JWT token for the user.
     *
     * @param u The User entity to be mapped.
     * @return A UserTokenDTO object containing user information and a generated JWT token.
     */
    private UserTokenDTO mapUserToken(User u) {
        // Generate a JWT token for the user.
        String token = jwtUtils.generateToken(u);
        // Create and return a UserTokenDTO from the user entity and the generated token.
        return UserTokenDTO.fromEntity(u, token);
    }

}
