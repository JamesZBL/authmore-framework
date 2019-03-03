package me.zbl.authmore.main;

import me.zbl.reactivesecurity.common.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes;
import static me.zbl.authmore.main.OAuthProperties.GrantTypes.AUTHORIZATION_CODE;
import static me.zbl.authmore.main.OAuthProperties.PARAM_CODE;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class ClientAuthorizationCodeTokenManager extends ClientAbstractTokenManager {

    public ClientAuthorizationCodeTokenManager(
            RestTemplate client,
            String clientId,
            String clientSecret,
            String tokenIssueUrl) {
        super(client, clientId, clientSecret, tokenIssueUrl);
    }

    @Override
    protected void enhanceQueryParams(Map<String, String> params) {
        super.enhanceQueryParams(params);
        String code = params.get(PARAM_CODE);
        Assert.notEmpty(code, "code cannot be empty");
    }

    @Override
    protected final GrantTypes getGrantType() {
        return AUTHORIZATION_CODE;
    }
}
