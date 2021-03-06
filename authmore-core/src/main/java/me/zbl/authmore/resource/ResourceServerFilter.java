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
package me.zbl.authmore.resource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import me.zbl.authmore.oauth.*;
import net.minidev.json.JSONArray;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
public class ResourceServerFilter extends OAuthFilter {

    private final ResourceServerConfigurationProperties resourceServerConfigurationProperties;

    public ResourceServerFilter(ResourceServerConfigurationProperties resourceServerConfigurationProperties) {
        this.resourceServerConfigurationProperties = resourceServerConfigurationProperties;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token;
        String tokenInfoUrl;
        String clientId;
        String clientSecret;
        TokenCheckResponse tokenInfo;
        try {
            token = OAuthUtil.extractToken(request);
        } catch (OAuthException e) {
            sendError(response, e.getMessage());
            return;
        }

        Set<String> scopes;
        Set<String> authorities;
        Set<String> resourceIds;
        String userId;
        long expireAt;

        if (isJWT(token)) {
            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
            JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(
                new URL(resourceServerConfigurationProperties.getJwkSetUrl()));
            JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
            JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);
            jwtProcessor.setJWSKeySelector(keySelector);
            JWTClaimsSet claimsSet;
            try {
                claimsSet = jwtProcessor.process(token, null);
            } catch (ParseException | BadJOSEException | JOSEException e) {
                e.printStackTrace();
                throw new OAuthException("Failed to verify token.");
            }
            scopes = extractSetFrom(claimsSet, OAuthProperties.TOKEN_SCOPES);
            authorities = extractSetFrom(claimsSet, OAuthProperties.TOKEN_AUTHORITIES);
            resourceIds = extractSetFrom(claimsSet, OAuthProperties.TOKEN_RESOURCE_IDS);
            userId = (String) claimsSet.getClaim(OAuthProperties.TOKEN_USER_ID);
            try {
                expireAt = claimsSet.getLongClaim(OAuthProperties.TOKEN_EXPIRE_AT);
            } catch (ParseException e) {
                throw new OAuthException("Error while parsing expire time");
            }
            if (System.currentTimeMillis() > expireAt) {
                throw new OAuthException("This token has been expired");
            }
        } else {
            tokenInfoUrl = resourceServerConfigurationProperties.getTokenInfoUrl();
            clientId = resourceServerConfigurationProperties.getClientId();
            clientSecret = resourceServerConfigurationProperties.getClientSecret();

            RestTemplate rest = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put(OAuthProperties.PARAM_CLIENT_ID, clientId);
            params.put(OAuthProperties.PARAM_CLIENT_SECRET, clientSecret);
            tokenInfo = rest.getForObject(tokenInfoUrl + "?" + RequestUtil.queryStringOf(params), TokenCheckResponse.class);
            if (null == tokenInfo) {
                sendError(response, "invalid token");
                return;
            }
            scopes = tokenInfo.getScope();
            authorities = tokenInfo.getAuthorities();
            resourceIds = tokenInfo.getResourceIds();
            userId = tokenInfo.getUserId();
        }

        request.setAttribute(OAuthProperties.REQUEST_SCOPES, scopes);
        request.setAttribute(OAuthProperties.REQUEST_AUTHORITIES, authorities);
        request.setAttribute(OAuthProperties.REQUEST_RESOURCE_IDS, resourceIds);
        request.setAttribute(OAuthProperties.REQUEST_USER_ID, userId);
        filterChain.doFilter(request, response);
    }

    private boolean isJWT(String token) {
        return token.startsWith("eyJ");
    }

    private Set<String> extractSetFrom(JWTClaimsSet claimsSet, String key) {
        JSONArray scopeArray = (JSONArray) claimsSet.getClaim(key);
        String[] scopesStrings = scopeArray.toArray(new String[0]);
        return Arrays.stream(scopesStrings).collect(Collectors.toSet());
    }
}
