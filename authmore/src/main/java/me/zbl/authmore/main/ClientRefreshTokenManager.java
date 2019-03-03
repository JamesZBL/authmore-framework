package me.zbl.authmore.main;

import me.zbl.reactivesecurity.common.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes;
import static me.zbl.authmore.main.OAuthProperties.GrantTypes.REFRESH_TOKEN;
import static me.zbl.authmore.main.OAuthProperties.PARAM_REFRESH_TOKEN;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class ClientRefreshTokenManager extends ClientAbstractTokenManager {

    public ClientRefreshTokenManager(
            RestTemplate client,
            String clientId,
            String clientSecret,
            String tokenIssueUrl) {
        super(client, clientId, clientSecret, tokenIssueUrl);
    }

    @Override
    protected void enhanceQueryParams(Map<String, String> params) {
        super.enhanceQueryParams(params);
        String refreshToken = params.get(PARAM_REFRESH_TOKEN);
        Assert.notEmpty(refreshToken, refreshToken);
    }

    @Override
    protected final GrantTypes getGrantType() {
        return REFRESH_TOKEN;
    }
}
