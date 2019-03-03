package me.zbl.authmore.main;

import me.zbl.authmore.core.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static me.zbl.authmore.main.SessionProperties.CURRENT_USER_DETAILS;
import static me.zbl.authmore.main.SessionProperties.LAST_URL;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-14
 */
@WebFilter(urlPatterns = {"/authorize"})
public final class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        UserDetails user = (UserDetails) session.getAttribute(CURRENT_USER_DETAILS);
        if (null == user) {
            redirectToSignin(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void redirectToSignin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        HttpSession session = request.getSession();
        session.setAttribute(LAST_URL, requestURI + "?" + queryString);
        response.sendRedirect("/signin");
    }
}
