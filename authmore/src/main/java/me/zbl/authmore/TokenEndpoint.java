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

import me.zbl.authmore.OAuthProperties.GrantTypes;
import me.zbl.reactivesecurity.auth.client.ClientDetails;
import me.zbl.reactivesecurity.auth.user.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static me.zbl.authmore.OAuthException.*;
import static me.zbl.authmore.OAuthProperties.GrantTypes.PASSWORD;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-18
 */
@RestController
public class TokenEndpoint {

    private ClientDetailsRepository clients;
    private UserDetailsRepository users;
    private CodeManager codeManager;
    private TokenManager tokenManager;
    private PasswordEncoder passwordEncoder;

    public TokenEndpoint(
            ClientDetailsRepository clients,
            UserDetailsRepository users, CodeManager codeManager,
            TokenManager tokenManager,
            PasswordEncoder passwordEncoder) {
        this.clients = clients;
        this.users = users;
        this.codeManager = codeManager;
        this.tokenManager = tokenManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/oauth/token")
    public TokenResponse token(
            @RequestParam(value = "grant_type", required = false) String grantType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "scope", required = false) String scope) {
        GrantTypes realType = GrantTypes.eval(grantType);
        TokenResponse token;
        if (isEmpty(clientId))
            throw new OAuthException(INVALID_CLIENT);
        ClientDetails client = clients.findByClientId(clientId)
                .orElseThrow(() -> new OAuthException(INVALID_CLIENT));
        String userId;
        Set<String> scopes;
        switch (realType) {
            case AUTHORIZATION_CODE:
                CodeBinding codeBinding = codeManager.getCodeDetails(clientId, code);
                scopes = codeBinding.getScopes();
                String requestRedirectUri = codeBinding.getRedirectUri();
                if (isEmpty(redirectUri) || !redirectUri.equals(requestRedirectUri)) {
                    throw new OAuthException(REDIRECT_URI_MISMATCH);
                }
                codeManager.expireCode(code);
                userId = codeBinding.getUserId();
                token = tokenManager.create(client, userId, scopes);
                break;
            case PASSWORD:
                OAuthUtil.validateClientAndGrantType(client, PASSWORD);
                UserDetails user = users.findByUsername(username)
                        .orElseThrow(() -> new OAuthException("invalid username"));
                boolean matches = passwordEncoder.matches(password, user.getPassword());
                if(!matches)
                    throw new OAuthException("invalid password");
                OAuthUtil.validateClientAndScope(client, scope);
                userId = user.getId();
                scopes = OAuthUtil.scopeSet(scope);
                token = tokenManager.create(client, userId, scopes);
                break;
            case CLIENT_CREDENTIALS:
                OAuthUtil.validateClientAndGrantType(client, GrantTypes.CLIENT_CREDENTIALS);
                OAuthUtil.validateClientAndScope(client, scope);
                scopes = OAuthUtil.scopeSet(scope);
                token = tokenManager.create(client, null, scopes);
                break;
            default:
                throw new OAuthException(UNSUPPORTED_GRANT_TYPE);
        }
        return token;
    }
}
