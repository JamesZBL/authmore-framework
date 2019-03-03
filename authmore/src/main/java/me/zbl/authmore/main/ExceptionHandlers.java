package me.zbl.authmore.main;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
@ControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AuthorizationException.class})
    public String authorizationException(AuthorizationException exception, Model model) {
        String error = exception.getMessage();
        model.addAttribute("error", error);
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({OAuthException.class})
    public OAuthErrorResponse oAuthException(OAuthException exception) {
        return new OAuthErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public OAuthErrorResponse badRequest(MissingServletRequestParameterException exception) {
        return new OAuthErrorResponse(exception);
    }
}
