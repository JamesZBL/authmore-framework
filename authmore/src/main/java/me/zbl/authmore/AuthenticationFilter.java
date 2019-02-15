/*
 * Copyright 2019 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.user.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static me.zbl.authmore.SessionProperties.LAST_URL;
import static me.zbl.authmore.SessionProperties.SESSION_DETAILS;

/**
 * @author JamesZBL
 * created at 2019-02-14
 */
@WebFilter(urlPatterns = {"/authorize"})
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        SessionDetails sessionDetails = (SessionDetails) session.getAttribute(SESSION_DETAILS);
        if (null == sessionDetails) {
            redirectToSignin(request, response);
            return;
        }

        UserDetails user = sessionDetails.getUser();
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