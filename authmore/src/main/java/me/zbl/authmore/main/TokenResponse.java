/*
 * Copyright 2019 ZHENG BAO LE
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

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public class TokenResponse {

    private String access_token;
    private long expires_in;
    private String refresh_token;
    private Set<String> scope;

    public TokenResponse() {}

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

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setScope(String scope) {
        this.scope = Arrays.stream(scope.split(",")).collect(Collectors.toSet());
    }
}
