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
package me.zbl.authmore.configuration;

import me.zbl.authmore.main.client.*;
import me.zbl.authmore.main.oauth.PasswordTokenManager;
import me.zbl.authmore.main.oauth.RefreshTokenManager;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
@Configuration
@ConditionalOnClass({ClientRestTemplate.class})
@EnableConfigurationProperties({ClientProperties.class})
public class TokenManagerAutoConfiguration implements SmartInitializingSingleton {

    private final String clientId;
    private final String clientSecret;
    private final String tokenIssueUrl;

    public TokenManagerAutoConfiguration(
            ClientProperties clientConfigurationProperties) {
        this.clientId = clientConfigurationProperties.getClientId();
        this.clientSecret = clientConfigurationProperties.getClientSecret();
        this.tokenIssueUrl = clientConfigurationProperties.getTokenIssueUrl();
    }

    @Bean
    @ConditionalOnMissingBean({AuthorizationCodeTokenManager.class})
    public AuthorizationCodeTokenManager clientAuthorizationCodeTokenManager() {
        return new AuthorizationCodeTokenManager(
                createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({PasswordTokenManager.class})
    public PasswordTokenManager clientPasswordTokenManager() {
        return new PasswordTokenManager(createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({ClientCredentialsTokenManager.class})
    public ClientCredentialsTokenManager clientClientCredentialsTokenManager() {
        return new ClientCredentialsTokenManager(
                createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({RefreshTokenManager.class})
    public RefreshTokenManager clientRefreshTokenManager() {
        return new RefreshTokenManager(createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    private RestTemplate createTokenRestTemplate() {
        return new ClientTokenRestTemplate();
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (isEmpty(clientId) || isEmpty(clientSecret) || isEmpty(tokenIssueUrl))
            throw new IllegalStateException("oauth client must specify a client-id, client-secret and token issue url");
    }
}
