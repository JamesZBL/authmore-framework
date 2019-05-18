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

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.client.ClientDetailsRepository;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Set;

import static me.zbl.authmore.main.oauth.OAuthProperties.*;
import static me.zbl.authmore.main.oauth.OAuthUtil.expireAtByLiveTime;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
public class JSONWebTokenManager extends AbstractTokenManager {

    private final KeyPair keyPair;

    public JSONWebTokenManager(ClientDetailsRepository clients, KeyPair keyPair) {
        super(clients);
        this.keyPair = keyPair;
    }

    @Override
    public TokenResponse create(ClientDetails client, String userId, Set<String> scopes) {
        assertValidateScopes(client, scopes);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim(TOKEN_USER_ID, userId)
                .claim(TOKEN_CLIENT_ID, client.getClientId())
                .claim(TOKEN_AUTHORITIES, client.getAuthoritySet())
                .claim(TOKEN_SCOPES, scopes)
                .claim(TOKEN_EXPIRE_AT, expireAtByLiveTime(client.getAccessTokenValiditySeconds()))
                .claim(TOKEN_RESOURCE_IDS, client.getResourceIds())
                .build();
        PrivateKey privateKey = keyPair.getPrivate();
        RSASSASigner signer = new RSASSASigner(privateKey);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), claims);
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new OAuthException("Failed to sign jwt.");
        }
        return new TokenResponse(signedJWT.serialize(), client.getAccessTokenValiditySeconds(), scopes);
    }

    @Override
    public TokenResponse refresh(String refreshToken) {
        return super.refresh(refreshToken);
    }

    @Override
    public AccessTokenBinding findAccessToken(String token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RefreshTokenBinding findRefreshToken(String token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveAccessToken(AccessTokenBinding accessTokenBinding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveRefreshToken(RefreshTokenBinding refreshTokenBinding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expireAccessToken(String token, long expireIn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expireRefreshToken(String token, long expireIn) {
        throw new UnsupportedOperationException();
    }
}
