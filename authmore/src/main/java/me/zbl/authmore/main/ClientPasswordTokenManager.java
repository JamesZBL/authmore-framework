/*
 * Copyright 2019 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore.main;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes.PASSWORD;
import static me.zbl.authmore.main.OAuthProperties.*;

/**
 * @author JamesZBL
 * @since 2019-03-02
 */
public class ClientPasswordTokenManager implements ClientTokenOperations {

    private final RestTemplate client;
    private final String clientId;
    private final String clientSecret;

    public ClientPasswordTokenManager(RestTemplate client, String clientId, String clientSecret) {
        this.client = client;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public TokenResponse getToken(String tokenIssueUrl,Map<String, String> params) {
        String grantType = PASSWORD.getName();
        String userName = params.get(PARAM_USERNAME);
        String password = params.get(PARAM_PASSWORD);
        String scope = params.get(PARAM_GRANT_TYPE);
        Assert.notEmpty(grantType, "grant_type cannot be empty");
        Assert.notEmpty(clientId, "client_id cannot be empty");
        Assert.notEmpty(clientSecret, "client_secret cannot be empty");
        Assert.notEmpty(userName, "username cannot be empty");
        Assert.notEmpty(password, "password cannot be empty");
        Assert.notEmpty(scope, "scope cannot be empty");
        params.put(PARAM_GRANT_TYPE, grantType);
        params.put(PARAM_CLIENT_ID, clientId);
        params.put(PARAM_CLIENT_SECRET, clientSecret);
        String url = tokenIssueUrl + "?" + RequestUtil.queryStringOf(params);
        return client.getForObject(url, TokenResponse.class);
    }
}
