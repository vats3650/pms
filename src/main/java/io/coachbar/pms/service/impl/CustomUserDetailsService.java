package io.coachbar.pms.service.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.coachbar.pms.model.User;
import io.coachbar.pms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user==null) {
            LOGGER.error("User not found in DB");
            new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.emptyList()
        );
    }

}
