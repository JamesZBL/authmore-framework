package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes.CLIENT_CREDENTIALS;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Component
final class TokenClientCredentialsTokenIssuer {

    private final TokenManager tokenManager;

    TokenClientCredentialsTokenIssuer(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    TokenResponse issue(ClientDetails client, String scope) {
        OAuthUtil.validateClientAndGrantType(client, CLIENT_CREDENTIALS);
        OAuthUtil.validateClientAndScope(client, scope);
        Set<String> scopes = OAuthUtil.scopeSet(scope);
        return tokenManager.create(client, null, scopes);
    }
}
