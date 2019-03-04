/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zbl.authmore.main.oauth;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.client.ClientDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static me.zbl.authmore.main.authorization.RequestProperties.CURRENT_CLIENT;
import static me.zbl.authmore.main.oauth.OAuthProperties.PARAM_CLIENT_ID;
import static me.zbl.authmore.main.oauth.OAuthProperties.PARAM_CLIENT_SECRET;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
@WebFilter(urlPatterns = {"/oauth/token", "/oauth/check_token"})
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final ClientDetailsRepository clients;
    private final PasswordEncoder passwordEncoder;

    public TokenAuthenticationFilter(ClientDetailsRepository clients, PasswordEncoder passwordEncoder) {
        this.clients = clients;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientId;
        String clientSecret;
        clientId = request.getParameter(PARAM_CLIENT_ID);
        clientSecret = request.getParameter(PARAM_CLIENT_SECRET);
        if (isEmpty(clientId) || isEmpty(clientSecret)) {
            String authorization = request.getHeader(AUTHORIZATION);
            if (null == authorization || !authorization.startsWith("Basic")) {
                sendUnauthorized(response);
                return;
            }
            Map<String, String> client = OAuthUtil.extractClientFrom(request);
            clientId = client.get(PARAM_CLIENT_ID);
            clientSecret = client.get(PARAM_CLIENT_SECRET);
        }
        if (isEmpty(clientId) || isEmpty(clientSecret)) {
            sendUnauthorized(response);
            return;
        }
        Optional<ClientDetails> find = clients.findByClientId(clientId);
        if (!find.isPresent()) {
            sendUnauthorized(response);
            return;
        }
        ClientDetails client = find.get();
        if (!passwordEncoder.matches(clientSecret, client.getPassword())) {
            sendUnauthorized(response);
            return;
        }
        request.setAttribute(CURRENT_CLIENT, client);
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
