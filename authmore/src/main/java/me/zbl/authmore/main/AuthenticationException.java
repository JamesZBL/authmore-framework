package me.zbl.authmore.main;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
public class AuthenticationException extends Exception {

    public static final String INVALID_USERNAME = "Invalid username";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String ACCOUNT_DISABLED = "Account is disabled";

    public AuthenticationException(String message) {
        super(message);
    }
}
