package io.coachbar.pms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.coachbar.pms.exception.InvalidUsernameOrPasswordException;
import io.coachbar.pms.exception.UsernameAlreadyInUseException;
import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;
import io.coachbar.pms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API")
public class AuthController {

    Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "To registter a new User")
    public String register(@RequestBody User user) throws UsernameAlreadyInUseException {
        LOGGER.info("Received request for new user registration");
        return authService.register(user);
    }

    @PostMapping("/login")
    @Operation(summary = "To login for existing User")
    public String login(@RequestBody LoginRequest loginRequest) throws InvalidUsernameOrPasswordException {
        LOGGER.info("Received login request for user: "+ loginRequest.getUsername());
        return authService.login(loginRequest);
    }

}
