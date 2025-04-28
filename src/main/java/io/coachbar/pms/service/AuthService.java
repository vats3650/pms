package io.coachbar.pms.service;

import io.coachbar.pms.exception.InvalidUsernameOrPasswordException;
import io.coachbar.pms.exception.UsernameAlreadyInUseException;
import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;

public interface AuthService {

    public String register(User user) throws UsernameAlreadyInUseException;
    public String login(LoginRequest loginRequest) throws InvalidUsernameOrPasswordException;

}
