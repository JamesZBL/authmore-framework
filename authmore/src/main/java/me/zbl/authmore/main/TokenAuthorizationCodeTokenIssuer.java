package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
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

    TokenAuthorizationCodeTokenIssuer(CodeManager codeManager, TokenManager tokenManager) {
        this.codeManager = codeManager;
        this.tokenManager = tokenManager;
    }

    TokenResponse issue(ClientDetails client, String redirectUri, String code) {
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
