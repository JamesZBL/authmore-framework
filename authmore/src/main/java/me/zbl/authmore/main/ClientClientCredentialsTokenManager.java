package me.zbl.authmore.main;

import org.springframework.web.client.RestTemplate;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes;
import static me.zbl.authmore.main.OAuthProperties.GrantTypes.CLIENT_CREDENTIALS;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class ClientClientCredentialsTokenManager extends ClientAbstractTokenManager {

    public ClientClientCredentialsTokenManager(
            RestTemplate client,
            String clientId,
            String clientSecret,
            String tokenIssueUrl) {
        super(client, clientId, clientSecret, tokenIssueUrl);
    }

    @Override
    protected final GrantTypes getGrantType() {
        return CLIENT_CREDENTIALS;
    }
}
