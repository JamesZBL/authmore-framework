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
package me.zbl.authmore.main;

import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author JamesZBL
 * @since 2019-02-28
 */
public class ResourceServerFilter extends OAuthFilter {

    private final ResourceServerConfigurationProperties resourceServerConfigurationProperties;

    public ResourceServerFilter(ResourceServerConfigurationProperties resourceServerConfigurationProperties) {
        this.resourceServerConfigurationProperties = resourceServerConfigurationProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token;
        String tokenInfoUrl;
        String clientId;
        String clientSecret;
        TokenCheckResponse tokenInfo;
        try {
            token = OAuthUtil.extractToken(request);
        } catch (Exception e) {
            reject(response);
            return;
        }
        tokenInfoUrl = resourceServerConfigurationProperties.getTokenInfoUrl();
        clientId = resourceServerConfigurationProperties.getClientId();
        clientSecret = resourceServerConfigurationProperties.getClientSecret();
        RestTemplate rest = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        tokenInfo = rest.getForObject(tokenInfoUrl, TokenCheckResponse.class, params);
        if (null == tokenInfo) {
            reject(response);
            return;
        }
        Set<String> existScopes = tokenInfo.getScope();
        request.setAttribute(OAuthProperties.REQUEST_SCOPES, existScopes);
        filterChain.doFilter(request, response);
    }
}
