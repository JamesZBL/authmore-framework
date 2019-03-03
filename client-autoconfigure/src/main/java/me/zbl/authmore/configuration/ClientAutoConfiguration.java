/*
 * Copyright 2019 ZHENG BAO LE
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
package me.zbl.authmore.configuration;

import me.zbl.authmore.main.*;
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
@EnableConfigurationProperties({ClientConfigurationProperties.class})
public class ClientAutoConfiguration implements SmartInitializingSingleton {

    private final String clientId;
    private final String clientSecret;
    private final String tokenIssueUrl;

    public ClientAutoConfiguration(ClientConfigurationProperties clientConfigurationProperties) {
        this.clientId = clientConfigurationProperties.getClientId();
        this.clientSecret = clientConfigurationProperties.getClientSecret();
        this.tokenIssueUrl = clientConfigurationProperties.getTokenIssueUrl();
    }

    @Bean
    @ConditionalOnMissingBean({ClientAuthorizationCodeTokenManager.class})
    public ClientAuthorizationCodeTokenManager clientAuthorizationCodeTokenManager() {
        return new ClientAuthorizationCodeTokenManager(
                createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({ClientPasswordTokenManager.class})
    public ClientPasswordTokenManager clientPasswordTokenManager() {
        return new ClientPasswordTokenManager(createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({ClientClientCredentialsTokenManager.class})
    public ClientClientCredentialsTokenManager clientClientCredentialsTokenManager() {
        return new ClientClientCredentialsTokenManager(
                createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
    }

    @Bean
    @ConditionalOnMissingBean({ClientRefreshTokenManager.class})
    public ClientRefreshTokenManager clientRefreshTokenManager() {
        return new ClientRefreshTokenManager(createTokenRestTemplate(), clientId, clientSecret, tokenIssueUrl);
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
