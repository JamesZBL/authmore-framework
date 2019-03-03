package me.zbl.authmore.main;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("authorization_error");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
