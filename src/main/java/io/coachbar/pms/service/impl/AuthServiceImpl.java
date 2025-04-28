package io.coachbar.pms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.coachbar.pms.exception.InvalidUsernameOrPasswordException;
import io.coachbar.pms.exception.UsernameAlreadyInUseException;
import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;
import io.coachbar.pms.repository.UserRepository;
import io.coachbar.pms.service.AuthService;
import io.coachbar.pms.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                            JwtUtil jwtUtil,
                            AuthenticationManager authenticationManager,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(User user) throws UsernameAlreadyInUseException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            LOGGER.error("Username already exists");
            throw new UsernameAlreadyInUseException("username already in use, please use other one");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        LOGGER.info("User registration completed");
        return "User registered successfully!";
    }

    @Override
    public String login(LoginRequest loginRequest) throws InvalidUsernameOrPasswordException {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidUsernameOrPasswordException("Invalid username or password");
        }

        User dbUser = userRepository.findByUsername(loginRequest.getUsername());
        if (dbUser == null) {
            LOGGER.error("User not found in DB");
            new UsernameNotFoundException("User not found");
        }
        return jwtUtil.generateToken(dbUser);
    }

}
