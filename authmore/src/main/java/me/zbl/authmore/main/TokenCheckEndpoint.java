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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-26
 */
@RestController
public class TokenCheckEndpoint {

    private final TokenManager tokenManager;
    private final UserDetailsRepository users;
    private final ClientDetailsRepository clients;

    public TokenCheckEndpoint(TokenManager tokenManager, UserDetailsRepository users, ClientDetailsRepository clients) {
        this.tokenManager = tokenManager;
        this.users = users;
        this.clients = clients;
    }

    @GetMapping("/oauth/check_token")
    public TokenCheckResponse checkToken(@RequestParam(value = "token", required = false) String token) {
        AccessTokenBinding accessTokenBinding = tokenManager.findAccessToken(token);
        String userId = accessTokenBinding.getUserId();
        String clientId = accessTokenBinding.getClientId();
        Set<String> authorities = new HashSet<>();
        if (null != userId) {
            UserDetails user = users.findById(userId).orElseThrow(() -> new OAuthException("no such user"));
            authorities.addAll(user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        }
        ClientDetails client = clients.findByClientId(clientId).orElseThrow(() -> new OAuthException("no such client"));
        authorities.addAll(
                client.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        return new TokenCheckResponse(accessTokenBinding, authorities);
    }
}
