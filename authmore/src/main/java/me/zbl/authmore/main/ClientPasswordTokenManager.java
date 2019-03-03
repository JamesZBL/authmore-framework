package me.zbl.authmore.main;

import me.zbl.reactivesecurity.common.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.zbl.authmore.main.OAuthProperties.*;
import static me.zbl.authmore.main.OAuthProperties.GrantTypes.PASSWORD;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class ClientPasswordTokenManager extends ClientAbstractTokenManager {

    public ClientPasswordTokenManager(
            RestTemplate client,
            String clientId,
            String clientSecret,
            String tokenIssueUrl) {
        super(client, clientId, clientSecret, tokenIssueUrl);
    }

    @Override
    protected void enhanceQueryParams(Map<String, String> params) {
        super.enhanceQueryParams(params);
        String userName = params.get(PARAM_USERNAME);
        String password = params.get(PARAM_PASSWORD);
        Assert.notEmpty(userName, "username cannot be empty");
        Assert.notEmpty(password, "password cannot be empty");
    }

    @Override
    protected final GrantTypes getGrantType() {
        return PASSWORD;
    }
}
