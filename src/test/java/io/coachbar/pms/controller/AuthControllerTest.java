package io.coachbar.pms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.coachbar.pms.exception.InvalidUsernameOrPasswordException;
import io.coachbar.pms.exception.UsernameAlreadyInUseException;
import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;
import io.coachbar.pms.service.AuthService;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_success() throws UsernameAlreadyInUseException {
        User user = new User();
        when(authService.register(any(User.class))).thenReturn("User registered successfully");

        String response = authController.register(user);

        assertEquals("User registered successfully", response);
        verify(authService, times(1)).register(user);
    }

    @Test
    void testLogin_success() throws InvalidUsernameOrPasswordException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpass");

        when(authService.login(any(LoginRequest.class))).thenReturn("Login successful");

        String response = authController.login(loginRequest);

        assertEquals("Login successful", response);
        verify(authService, times(1)).login(loginRequest);
    }

}
