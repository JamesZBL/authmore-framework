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
