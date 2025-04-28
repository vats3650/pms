package io.coachbar.pms.exception;

public class InvalidUsernameOrPasswordException extends Exception {
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
