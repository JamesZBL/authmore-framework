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
package me.zbl.authmore.client;

import me.zbl.authmore.common.Assert;
import me.zbl.authmore.oauth.OAuthProperties;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class RefreshTokenManager extends AbstractClientTokenManager {

    public RefreshTokenManager(
            RestTemplate client,
            String clientId,
            String clientSecret,
            String tokenIssueUrl) {
        super(client, clientId, clientSecret, tokenIssueUrl);
    }

    @Override
    protected void enhanceQueryParams(Map<String, String> params) {
        super.enhanceQueryParams(params);
        String refreshToken = params.get(OAuthProperties.PARAM_REFRESH_TOKEN);
        Assert.notEmpty(refreshToken, refreshToken);
    }

    @Override
    protected final OAuthProperties.GrantTypes getGrantType() {
        return OAuthProperties.GrantTypes.REFRESH_TOKEN;
    }
}
