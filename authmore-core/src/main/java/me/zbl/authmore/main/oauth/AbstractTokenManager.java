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

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.client.ClientDetailsRepository;
import me.zbl.reactivesecurity.common.UniqueToken;

import java.util.Set;

import static me.zbl.authmore.main.oauth.OAuthException.INVALID_CLIENT;
import static me.zbl.authmore.main.oauth.OAuthException.INVALID_SCOPE;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
public abstract class AbstractTokenManager implements TokenManager {

    private final ClientDetailsRepository clients;

    public AbstractTokenManager(ClientDetailsRepository clients) {
        this.clients = clients;
    }

    @Override
    public TokenResponse create(ClientDetails client, String userId, Set<String> scopes) {
        String clientId = client.getClientId();
        long expireIn = client.getAccessTokenValiditySeconds();
        assertValidateScopes(client, scopes);
        AccessTokenBinding accessTokenBinding = createAccessTokenBinding(clientId, scopes, userId);
        String refreshToken = UniqueToken.create();
        RefreshTokenBinding refreshTokenBinding = new RefreshTokenBinding(refreshToken, clientId, scopes, userId);
        saveAccessToken(accessTokenBinding);
        saveRefreshToken(refreshTokenBinding);
        expireAccessToken(accessTokenBinding.getAccessToken(), expireIn);
        long expireAt = OAuthUtil.expireAtByLiveTime(expireIn);
        accessTokenBinding.setExpire(expireAt);
        Integer refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
        if (null != refreshTokenValiditySeconds && 0 != refreshTokenValiditySeconds) {
            expireRefreshToken(refreshToken, refreshTokenValiditySeconds);
        }
        saveAccessToken(accessTokenBinding);
        return new TokenResponse(accessTokenBinding.getAccessToken(), expireIn, refreshToken, scopes);
    }

    @Override
    public TokenResponse refresh(String refreshToken) {
        RefreshTokenBinding refreshTokenBinding = findRefreshToken(refreshToken);
        String clientId = refreshTokenBinding.getClientId();
        ClientDetails client = clients.findByClientId(clientId).orElseThrow(() -> new OAuthException(INVALID_CLIENT));
        long expireIn = client.getAccessTokenValiditySeconds();
        String newAccessToken = UniqueToken.create();
        refreshTokenBinding = freshRefreshTokenBinding(client, refreshTokenBinding);
        TokenResponse newTokenResponse = new TokenResponse(refreshTokenBinding, newAccessToken, expireIn);
        AccessTokenBinding newAccessTokenBinding = new AccessTokenBinding(refreshTokenBinding, newAccessToken);
        saveAccessToken(newAccessTokenBinding);
        expireAccessToken(newAccessToken, expireIn);
        long expireAt = OAuthUtil.expireAtByLiveTime(expireIn);
        newAccessTokenBinding.setExpire(expireAt);
        saveAccessToken(newAccessTokenBinding);
        return newTokenResponse;
    }

    private AccessTokenBinding createAccessTokenBinding(String clientId, Set<String> scopes, String userId) {
        String accessToken = UniqueToken.create();
        return new AccessTokenBinding(accessToken, clientId, scopes, userId);
    }

    protected void assertValidateScopes(ClientDetails client, Set<String> scopes) {
        boolean validScope = client.getScope().containsAll(scopes);
        if (!validScope)
            throw new OAuthException(INVALID_SCOPE);
    }

    @Override
    public RefreshTokenBinding freshRefreshTokenBinding(ClientDetails client, RefreshTokenBinding refreshTokenBinding) {
        Integer refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
        if (null != refreshTokenValiditySeconds && 0 != refreshTokenValiditySeconds) {
            String newRefreshToken = UniqueToken.create();
            Set<String> scopes = refreshTokenBinding.getScopes();
            String userId = refreshTokenBinding.getUserId();
            String clientId = client.getClientId();
            refreshTokenBinding = new RefreshTokenBinding(newRefreshToken, clientId, scopes, userId);
            saveRefreshToken(refreshTokenBinding);
            expireRefreshToken(newRefreshToken, refreshTokenValiditySeconds);
        }
        return refreshTokenBinding;
    }
}
