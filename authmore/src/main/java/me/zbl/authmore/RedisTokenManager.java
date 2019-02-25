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

import static me.zbl.authmore.OAuthException.INVALID_SCOPE;

/**
 * @author JamesZBL
 * @since 2019-02-21
 */
@Component
public class RedisTokenManager implements TokenManager {

    private AccessTokenRepository tokens;
    private RedisTemplate<String, String> redisTemplate;

    public RedisTokenManager(AccessTokenRepository tokens, RedisTemplate<String, String> redisTemplate) {
        this.tokens = tokens;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public TokenResponse create(ClientDetails client, String userId, Set<String> scopes) {
        String clientId = client.getClientId();
        long expireIn = client.getAccessTokenValiditySeconds();
        boolean validScope = client.getScope().containsAll(scopes);
        if(!validScope)
            throw new OAuthException(INVALID_SCOPE);
        String accessToken = RandomSecret.create();
        String refreshToken = RandomSecret.create();
        AccessTokenBinding accessTokenBinding = new AccessTokenBinding(accessToken, clientId, scopes, userId);
        tokens.save(accessTokenBinding);
        redisTemplate.boundHashOps(OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING + ":" + accessToken)
                .expire(expireIn, TimeUnit.SECONDS);
        return new TokenResponse(accessToken, expireIn, refreshToken, scopes);
    }

    @Override
    public AccessTokenBinding find(String token) {
        return tokens.findById(token).orElseThrow(() -> new OAuthException("invalid token"));
    }
}
