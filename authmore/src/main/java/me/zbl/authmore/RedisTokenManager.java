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
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.client.ClientDetails;
import me.zbl.reactivesecurity.common.RandomSecret;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static me.zbl.authmore.OAuthException.*;

/**
 * @author JamesZBL
 * @since 2019-02-21
 */
@Component
public class RedisTokenManager implements TokenManager {

    private AccessTokenRepository tokens;
    private RefreshTokenRepository refreshTokens;
    private RedisTemplate<String, String> redisTemplate;
    private ClientDetailsRepository clients;

    public RedisTokenManager(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate,
            ClientDetailsRepository clients) {
        this.tokens = tokens;
        this.refreshTokens = refreshTokens;
        this.redisTemplate = redisTemplate;
        this.clients = clients;
    }

    @Override
    public TokenResponse create(ClientDetails client, String userId, Set<String> scopes) {
        String clientId = client.getClientId();
        long expireIn = client.getAccessTokenValiditySeconds();
        boolean validScope = client.getScope().containsAll(scopes);
        if (!validScope)
            throw new OAuthException(INVALID_SCOPE);
        String accessToken = RandomSecret.create();
        String refreshToken = RandomSecret.create();
        AccessTokenBinding accessTokenBinding = new AccessTokenBinding(accessToken, clientId, scopes, userId);
        RefreshTokenBinding refreshTokenBinding = new RefreshTokenBinding(refreshToken, clientId, scopes, userId);
        tokens.save(accessTokenBinding);
        refreshTokens.save(refreshTokenBinding);
        redisTemplate.boundHashOps(OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING + ":" + accessToken)
                .expire(expireIn, TimeUnit.SECONDS);
        return new TokenResponse(accessToken, expireIn, refreshToken, scopes);
    }

    @Override
    public TokenResponse refresh(String refreshToken) {
        RefreshTokenBinding refreshTokenBinding = refreshTokens.findById(refreshToken)
                .orElseThrow(() -> new OAuthException(INVALID_TOKEN));
        String clientId = refreshTokenBinding.getClientId();
        ClientDetails client = clients.findByClientId(clientId).orElseThrow(() -> new OAuthException(INVALID_CLIENT));
        long accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
        String newAccessToken = RandomSecret.create();
        TokenResponse newTokenResponse =
                new TokenResponse(refreshTokenBinding, newAccessToken, accessTokenValiditySeconds);
        AccessTokenBinding newAccessTokenBinding = new AccessTokenBinding(refreshTokenBinding, newAccessToken);
        tokens.save(newAccessTokenBinding);
        return newTokenResponse;
    }

    @Override
    public AccessTokenBinding find(String token) {
        return tokens.findById(token).orElseThrow(() -> new OAuthException("invalid token"));
    }
}
