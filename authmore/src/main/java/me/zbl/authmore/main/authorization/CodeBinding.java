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
package me.zbl.authmore.main.authorization;

import me.zbl.authmore.main.oauth.OAuthProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
@RedisHash(value = OAuthProperties.KEY_PREFIX_CODE_BINDING, timeToLive = OAuthProperties.CODE_VALIDITY_SECONDS)
public final class CodeBinding implements Serializable {

    @Id
    private final String code;
    private final String clientId;
    private final Set<String> scopes;
    private final String redirectUri;
    private final String userId;

    public CodeBinding(String code, String clientId, Set<String> scopes, String redirectUri, String userId) {
        this.code = code;
        this.clientId = clientId;
        this.scopes = scopes;
        this.redirectUri = redirectUri;
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getUserId() {
        return userId;
    }

}
