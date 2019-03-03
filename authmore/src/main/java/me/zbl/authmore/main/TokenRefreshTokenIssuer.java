/*
 * Copyright 2019 ZHENG BAO LE
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
package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.stereotype.Component;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes.REFRESH_TOKEN;

/**
 * @author JamesZBL
 * @since 2019-03-03
 */
@Component
final class TokenRefreshTokenIssuer {

    private final TokenManager tokenManager;

    public TokenRefreshTokenIssuer(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    TokenResponse issue(ClientDetails client, String refreshToken) {
        OAuthUtil.validateClientAndGrantType(client, REFRESH_TOKEN);
        return tokenManager.refresh(refreshToken);
    }
}
