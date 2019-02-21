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

import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

/**
 * @author JamesZBL
 * @since 2019-02-21
 */
@RedisHash(value = OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING,
           timeToLive = OAuthProperties.DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS)
public class AccessTokenBinding {

    private String accessToken;
    private String clientId;
    private Set<String> scopes;
    private String userId;

    public AccessTokenBinding(String accessToken, String clientId, Set<String> scopes, String userId) {
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.scopes = scopes;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
