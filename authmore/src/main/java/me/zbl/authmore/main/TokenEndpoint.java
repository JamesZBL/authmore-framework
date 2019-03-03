package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.OAuthProperties.GrantTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static me.zbl.authmore.main.OAuthException.INVALID_CLIENT;
import static me.zbl.authmore.main.OAuthException.UNSUPPORTED_GRANT_TYPE;
import static me.zbl.authmore.main.RequestProperties.CURRENT_CLIENT;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
@RestController
public class TokenEndpoint {

    private final TokenAuthorizationCodeTokenIssuer authorizationCodeTokenIssuer;
    private final TokenPasswordTokenIssuer passwordTokenIssuer;
    private final TokenClientCredentialsTokenIssuer clientCredentialsTokenIssuer;
    private final TokenRefreshTokenIssuer refreshTokenIssuer;

    public TokenEndpoint(
            TokenAuthorizationCodeTokenIssuer authorizationCodeTokenIssuer,
            TokenPasswordTokenIssuer passwordTokenIssuer,
            TokenClientCredentialsTokenIssuer clientCredentialsTokenIssuer,
            TokenRefreshTokenIssuer refreshTokenIssuer) {
        this.authorizationCodeTokenIssuer = authorizationCodeTokenIssuer;
        this.passwordTokenIssuer = passwordTokenIssuer;
        this.clientCredentialsTokenIssuer = clientCredentialsTokenIssuer;
        this.refreshTokenIssuer = refreshTokenIssuer;
    }

    @PostMapping("/oauth/token")
    public TokenResponse token(
            @RequestParam(value = "grant_type", required = false) String grantType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @RequestAttribute(CURRENT_CLIENT) ClientDetails client) {
        GrantTypes realType = GrantTypes.eval(grantType);
        if (isEmpty(clientId))
            throw new OAuthException(INVALID_CLIENT);
        switch (realType) {
            case AUTHORIZATION_CODE:
                return authorizationCodeTokenIssuer.issue(client, redirectUri, code);
            case PASSWORD:
                return passwordTokenIssuer.issue(client, username, password, scope);
            case CLIENT_CREDENTIALS:
                return clientCredentialsTokenIssuer.issue(client, scope);
            case REFRESH_TOKEN:
                return refreshTokenIssuer.issue(client, refreshToken);
            default:
                throw new OAuthException(UNSUPPORTED_GRANT_TYPE);
        }
    }
}
