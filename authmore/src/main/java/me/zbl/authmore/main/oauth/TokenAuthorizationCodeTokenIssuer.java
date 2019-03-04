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
import me.zbl.authmore.main.code.CodeBinding;
import me.zbl.authmore.main.code.CodeManager;
import me.zbl.authmore.main.token.TokenManager;
import me.zbl.authmore.main.token.TokenResponse;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
@Component
final class TokenAuthorizationCodeTokenIssuer {

    private final CodeManager codeManager;
    private final TokenManager tokenManager;

    public TokenAuthorizationCodeTokenIssuer(CodeManager codeManager, TokenManager tokenManager) {
        this.codeManager = codeManager;
        this.tokenManager = tokenManager;
    }

    public TokenResponse issue(ClientDetails client, String redirectUri, String code) {
        CodeBinding codeBinding = codeManager.getCodeDetails(client.getClientId(), code);
        Set<String> scopes = codeBinding.getScopes();
        String requestRedirectUri = codeBinding.getRedirectUri();
        if (isEmpty(redirectUri) || !redirectUri.equals(requestRedirectUri)) {
            throw new OAuthException(OAuthException.REDIRECT_URI_MISMATCH);
        }
        codeManager.expireCode(code);
        String userId = codeBinding.getUserId();
        return tokenManager.create(client, userId, scopes);
    }
}
