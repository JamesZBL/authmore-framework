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

import java.util.Set;

/**
 * @author JamesZBL
 * @since 2019-02-18
 */
public class TokenResponse {

    private final String access_token;
    private final long expires_in;
    private final String refresh_token;
    private final Set<String> scope;

    public TokenResponse(String access_token, long expires_in, String refresh_token, Set<String> scope) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
    }

    public TokenResponse(RefreshTokenBinding refreshTokenBinding, String access_token, long expires_in) {
        Set<String> scopes = refreshTokenBinding.getScopes();
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refreshTokenBinding.getRefreshToken();
        this.scope = scopes;
    }

    public String getAccess_token() {
        return access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return String.join(",", scope);
    }
}
