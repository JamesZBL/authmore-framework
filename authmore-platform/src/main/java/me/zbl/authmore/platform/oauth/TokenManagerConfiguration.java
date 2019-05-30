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
package me.zbl.authmore.platform.oauth;

import me.zbl.authmore.oauth.CodeManager;
import me.zbl.authmore.oauth.JSONWebTokenManager;
import me.zbl.authmore.oauth.RedisTokenManager;
import me.zbl.authmore.oauth.TokenManager;
import me.zbl.authmore.platform.authorization.CodeRepository;
import me.zbl.authmore.platform.authorization.RedisCodeManager;
import me.zbl.authmore.platform.oauth.TokenConfigurationProperties.TokenPolicy;
import me.zbl.authmore.repositories.AccessTokenRepository;
import me.zbl.authmore.repositories.ClientDetailsRepository;
import me.zbl.authmore.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.KeyPair;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Configuration
public class TokenManagerConfiguration {

    private final TokenConfigurationProperties tokenConfiguration;
    private final KeyPair keyPair;

    @Autowired
    public TokenManagerConfiguration(TokenConfigurationProperties tokenConfiguration, KeyPair keyPair) {
        this.tokenConfiguration = tokenConfiguration;
        this.keyPair = keyPair;
    }

    @Bean
    @ConditionalOnMissingBean({TokenManager.class})
    public TokenManager tokenManager(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate, ClientDetailsRepository clients) {
        if (null != tokenConfiguration && tokenConfiguration.getPolicy() == TokenPolicy.JWT)
            return new JSONWebTokenManager(clients, keyPair);
        return new RedisTokenManager(tokens, refreshTokens, redisTemplate, clients);
    }

    @Bean
    @ConditionalOnMissingBean({CodeManager.class})
    public CodeManager codeManager(CodeRepository codes) {
        return new RedisCodeManager(codes);
    }
}
