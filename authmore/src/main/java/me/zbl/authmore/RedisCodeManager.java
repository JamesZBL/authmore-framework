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
import org.springframework.stereotype.Component;

import java.util.Set;

import static me.zbl.authmore.OAuthException.INVALID_CODE;

/**
 * @author JamesZBL
 * @since 2019-02-18
 */
@Component
public class RedisCodeManager implements CodeManager {

    private AuthorizationCodeRepository authorizationCodes;

    public RedisCodeManager(AuthorizationCodeRepository authorizationCodes) {
        this.authorizationCodes = authorizationCodes;
    }

    @Override
    public void saveCodeBinding(ClientDetails client, String code, Set<String> scopes) {
        String clientId = client.getClientId();
        AuthorizationCode authorizationCode = new AuthorizationCode(code, clientId, scopes);
        authorizationCodes.save(authorizationCode);
    }

    @Override
    public Set<String> getCurrentScopes(String clientId, String code) {
        AuthorizationCode find = authorizationCodes.findById(code)
                .orElseThrow(() -> new OAuthException(INVALID_CODE));
        return find.getScopes();
    }
}
