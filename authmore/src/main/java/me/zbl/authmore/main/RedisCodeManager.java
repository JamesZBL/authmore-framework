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

import me.zbl.authmore.core.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

import static me.zbl.authmore.main.OAuthException.INVALID_CODE;

/**
 * @author JamesZBL
 * @since 2019-02-18
 */
@Component
public class RedisCodeManager implements CodeManager {

    private final CodeRepository authorizationCodes;

    public RedisCodeManager(CodeRepository authorizationCodes) {
        this.authorizationCodes = authorizationCodes;
    }

    @Override
    public void saveCodeBinding(ClientDetails client, String code, Set<String> scopes, String redirectUri, String userId) {
        String clientId = client.getClientId();
        CodeBinding codeBinding = new CodeBinding(code, clientId, scopes, redirectUri, userId);
        authorizationCodes.save(codeBinding);
    }

    @Override
    public CodeBinding getCodeDetails(String clientId, String code) {
        return authorizationCodes.findById(code)
                .orElseThrow(() -> new OAuthException(INVALID_CODE));
    }

    @Override
    public void expireCode(String code) {
        authorizationCodes.deleteById(code);
    }
}
