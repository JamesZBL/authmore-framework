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
package me.zbl.authmore.main;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Configuration
public class TokenManagerConfiguration {

    private final AccessTokenRepository tokens;
    private final RefreshTokenRepository refreshTokens;
    private final RedisTemplate<String, String> redisTemplate;
    private final ClientDetailsRepository clients;
    private final CodeRepository codes;

    public TokenManagerConfiguration(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate,
            ClientDetailsRepository clients,
            CodeRepository codes) {
        this.tokens = tokens;
        this.refreshTokens = refreshTokens;
        this.redisTemplate = redisTemplate;
        this.clients = clients;
        this.codes = codes;
    }

    @Bean
    @ConditionalOnMissingBean({TokenManager.class})
    public TokenManager tokenManager() {
        return new RedisTokenManager(tokens, refreshTokens, redisTemplate, clients);
    }

    @Bean
    @ConditionalOnMissingBean({CodeManager.class})
    public CodeManager codeManager() {
        return new RedisCodeManager(codes);
    }
}
