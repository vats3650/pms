package io.coachbar.pms.exception;

public class UsernameAlreadyInUseException extends Exception {
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
