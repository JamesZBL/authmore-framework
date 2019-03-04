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
package me.zbl.authmore.main.token;

import me.zbl.authmore.main.client.AbstractTokenManager;
import me.zbl.reactivesecurity.common.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.zbl.authmore.main.oauth.OAuthProperties.*;
import static me.zbl.authmore.main.oauth.OAuthProperties.GrantTypes.PASSWORD;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class PasswordTokenManager extends AbstractTokenManager {

    public PasswordTokenManager(
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
