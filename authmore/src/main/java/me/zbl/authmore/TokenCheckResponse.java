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
 * @since 2019-02-26
 */
public class TokenCheckResponse {

    private final Set<String> scope;
    private final long exp;
    private final String client_id;

    public TokenCheckResponse(Set<String> scope, long exp, String client_id) {
        this.scope = scope;
        this.exp = exp;
        this.client_id = client_id;
    }

    public TokenCheckResponse(AccessTokenBinding tokenBinding) {
        this.scope = tokenBinding.getScopes();
        this.exp = tokenBinding.getExpire();
        this.client_id = tokenBinding.getClientId();
    }

    public Set<String> getScope() {
        return scope;
    }

    public long getExp() {
        return exp;
    }

    public String getClient_id() {
        return client_id;
    }
}
