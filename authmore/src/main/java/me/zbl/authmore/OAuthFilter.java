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

import me.zbl.reactivesecurity.auth.client.ClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static me.zbl.authmore.OAuthProperties.REQUEST_AUTHORITIES;
import static me.zbl.authmore.OAuthProperties.REQUEST_SCOPES;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-25
 */
@WebFilter(urlPatterns = {"/user/details"})
public class OAuthFilter extends OncePerRequestFilter {

    private final TokenManager tokens;
    private final ClientDetailsRepository clients;

    public OAuthFilter(TokenManager tokens, ClientDetailsRepository clients) {
        this.tokens = tokens;
        this.clients = clients;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AccessTokenBinding accessTokenBinding;
        String authorization = request.getHeader("Authorization");
        if (isEmpty(authorization) || !authorization.startsWith("Bearer")) {
            sendUnauthorized(response);
            return;
        }
        String[] words = authorization.split(" ");
        if (2 > words.length) {
            sendUnauthorized(response);
            return;
        }
        String token = words[1];
        try {
            accessTokenBinding = tokens.find(token);
        } catch (OAuthException e) {
            sendUnauthorized(response);
            return;
        }
        String clientId = accessTokenBinding.getClientId();
        ClientDetails client = clients.findByClientId(clientId)
                .orElseThrow(() -> new OAuthException(OAuthException.INVALID_CLIENT));
        Set<String> scopes = accessTokenBinding.getScopes();
        Set<String> authorities = client.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        request.setAttribute(REQUEST_SCOPES, scopes);
        request.setAttribute(REQUEST_AUTHORITIES, authorities);
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
