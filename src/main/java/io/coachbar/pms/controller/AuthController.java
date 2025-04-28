package io.coachbar.pms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;
import io.coachbar.pms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "To registter a new User")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    @Operation(summary = "To login for existing User")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
