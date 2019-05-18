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
package me.zbl.authmore.main.oauth;

import me.zbl.authmore.main.client.ClientDetailsRepository;
import me.zbl.authmore.main.repositories.AccessTokenRepository;
import me.zbl.authmore.main.repositories.RefreshTokenRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static me.zbl.authmore.main.oauth.OAuthException.INVALID_TOKEN;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
public class RedisTokenManager extends AbstractTokenManager {

    private final AccessTokenRepository tokens;
    private final RefreshTokenRepository refreshTokens;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisTokenManager(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate,
            ClientDetailsRepository clients) {
        super(clients);
        this.tokens = tokens;
        this.refreshTokens = refreshTokens;
        this.redisTemplate = redisTemplate;
    }

    private void expireToken(String keyPrefix, String token, long expireIn) {
        redisTemplate.boundHashOps(keyPrefix + ":" + token).expire(expireIn, TimeUnit.SECONDS);
    }

    @Override
    public AccessTokenBinding findAccessToken(String token) {
        return tokens.findById(token).orElseThrow(() -> new OAuthException(INVALID_TOKEN));
    }

    @Override
    public RefreshTokenBinding findRefreshToken(String token) {
        return refreshTokens.findById(token).orElseThrow(() -> new OAuthException(INVALID_TOKEN));
    }

    @Override
    public void saveAccessToken(AccessTokenBinding accessTokenBinding) {
        tokens.save(accessTokenBinding);
    }

    @Override
    public void saveRefreshToken(RefreshTokenBinding refreshTokenBinding) {
        refreshTokens.save(refreshTokenBinding);
    }

    @Override
    public void expireAccessToken(String token, long expireIn) {
        expireToken(OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING, token, expireIn);
    }

    @Override
    public void expireRefreshToken(String token, long expireIn) {
        expireToken(OAuthProperties.KEY_PREFIX_REFRESH_TOKEN_BINDING, token, expireIn);
    }
}
