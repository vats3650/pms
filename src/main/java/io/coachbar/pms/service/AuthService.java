package io.coachbar.pms.service;

import io.coachbar.pms.model.LoginRequest;
import io.coachbar.pms.model.User;

public interface AuthService {

    public String register(User user);
    public String login(LoginRequest loginRequest);

}
