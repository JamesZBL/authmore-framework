package me.zbl.authmore.main;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
public final class OAuthErrorResponse {

    private final String error;
    private final String error_description;

    public OAuthErrorResponse(OAuthException e) {
        this(e.getMessage(), e.getErrorDescription());
    }

    public OAuthErrorResponse(MissingServletRequestParameterException e) {
        this("invalid request parameters", e.getMessage());
    }

    public OAuthErrorResponse(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }
}
