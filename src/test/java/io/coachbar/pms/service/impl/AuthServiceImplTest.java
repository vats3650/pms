package io.coachbar.pms.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.coachbar.pms.exception.InvalidUsernameOrPasswordException;
import io.coachbar.pms.exception.UsernameAlreadyInUseException;
import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;
import io.coachbar.pms.repository.UserRepository;
import io.coachbar.pms.util.JwtUtil;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() throws UsernameAlreadyInUseException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String response = authServiceImpl.register(user);

        assertEquals("User registered successfully!", response);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void register_usernameAlreadyExists_throwsException() {
        User user = new User();
        user.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(user);

        UsernameAlreadyInUseException exception = assertThrows(UsernameAlreadyInUseException.class, () -> authServiceImpl.register(user));
        assertEquals("username already in use, please use other one", exception.getMessage());
    }

    @Test
    void login_success() throws InvalidUsernameOrPasswordException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        User dbUser = new User();
        dbUser.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(dbUser);
        when(jwtUtil.generateToken(dbUser)).thenReturn("jwtToken");

        String token = authServiceImpl.login(loginRequest);

        assertEquals("jwtToken", token);
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_invalidCredentials_throwsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalid");
        loginRequest.setPassword("wrong");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(InvalidUsernameOrPasswordException.class, () -> authServiceImpl.login(loginRequest));
    }

}
