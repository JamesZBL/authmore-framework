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
package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static me.zbl.authmore.main.OAuthProperties.*;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-25
 */
@WebFilter(urlPatterns = {"/user/details"})
public class OAuthUserProfileFilter extends OAuthFilter {

    private final TokenManager tokens;
    private final ClientDetailsRepository clients;
    private final UserDetailsRepository users;

    public OAuthUserProfileFilter(TokenManager tokens, ClientDetailsRepository clients, UserDetailsRepository users) {
        this.tokens = tokens;
        this.clients = clients;
        this.users = users;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AccessTokenBinding accessTokenBinding;
        Set<String> authorities;
        Set<String> scopes;
        Set<String> resourceIds;
        String token;
        try {
            token = OAuthUtil.extractToken(request);
        } catch (Exception e) {
            reject(response);
            return;
        }
        try {
            accessTokenBinding = tokens.findAccessToken(token);
        } catch (OAuthException e) {
            reject(response);
            return;
        }
        String clientId = accessTokenBinding.getClientId();
        ClientDetails client = clients.findByClientId(clientId)
                .orElseThrow(() -> new OAuthException(OAuthException.INVALID_CLIENT));
        scopes = accessTokenBinding.getScopes();
        resourceIds = client.getResourceIds();
        String userId = accessTokenBinding.getUserId();
        if (null != userId) {
            UserDetails user = users.findById(userId).orElseThrow(() -> new OAuthException("no such user"));
            authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        } else {
            authorities = client.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        }
        request.setAttribute(REQUEST_SCOPES, scopes);
        request.setAttribute(REQUEST_AUTHORITIES, authorities);
        request.setAttribute(REQUEST_RESOURCE_IDS, resourceIds);
        filterChain.doFilter(request, response);
    }
}
