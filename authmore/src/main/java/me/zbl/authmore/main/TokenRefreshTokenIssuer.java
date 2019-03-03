package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.stereotype.Component;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes.REFRESH_TOKEN;

/**
 * @author ZHENG BAO LE
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
