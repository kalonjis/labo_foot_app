package com.labospring.LaboFootApp.pl.controller.security;

import com.labospring.LaboFootApp.bll.security.AuthService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.il.utils.JwtUtils;
import com.labospring.LaboFootApp.pl.models.user.UserCreateForm;
import com.labospring.LaboFootApp.pl.models.user.UserLoginForm;
import com.labospring.LaboFootApp.pl.models.user.UserTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
    public ResponseEntity<UserTokenDTO> register(@Valid @RequestBody UserCreateForm form) {
        // Register the user using the provided form and obtain the newly registered user object.
        User u = authService.register(form.toUser());
        // Map the user to a UserTokenDTO (which includes a JWT token) and return it in the response.
        return ResponseEntity.ok(mapUserToken(u));
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
    public ResponseEntity<UserTokenDTO> login(@RequestBody UserLoginForm form) {
        // Log in the user using the provided username and password, and obtain the user object.
        User u = authService.login(form.username(), form.password());
        // Map the user to a UserTokenDTO (which includes a JWT token) and return it in the response.
        return ResponseEntity.ok(mapUserToken(u));
    }

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
