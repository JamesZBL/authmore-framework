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
import me.zbl.authmore.core.UserDetails;
import me.zbl.authmore.main.authorization.UserDetailsRepository;
import me.zbl.authmore.main.token.TokenManager;
import me.zbl.authmore.main.token.TokenResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

import static me.zbl.authmore.main.oauth.OAuthProperties.GrantTypes.PASSWORD;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Component
public final class TokenPasswordTokenIssuer {

    private final UserDetailsRepository users;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public TokenPasswordTokenIssuer(
            UserDetailsRepository users,
            PasswordEncoder passwordEncoder,
            TokenManager tokenManager) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
    }

    public TokenResponse issue(ClientDetails client, String username, String password, String scope) {
        OAuthUtil.validateClientAndGrantType(client, PASSWORD);
        UserDetails user = users.findByUsername(username)
                .orElseThrow(() -> new OAuthException("invalid username"));
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches)
            throw new OAuthException("invalid password");
        OAuthUtil.validateClientAndScope(client, scope);
        String userId = user.getId();
        Set<String> scopes = OAuthUtil.scopeSet(scope);
        return tokenManager.create(client, userId, scopes);
    }
}
