package me.zbl.authmore.main;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public abstract class OAuthFilter extends OncePerRequestFilter {

    public void reject(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "oauth unauthorized");
    }
}
