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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author JamesZBL
 * @since 2019-02-18
 */
@Component
public class RedisCodeManager extends AbstractKeyManager implements CodeManager {

    private static final String KEY_PREFIX = "authmore:authorization:code:";

    private RedisTemplate<String, String> redisTemplate;

    public RedisCodeManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    @Override
    public void saveCodeBinding(ClientDetails client, String code, Set<String> scopes) {
        redisTemplate.boundValueOps(key(code) + ":client_id").set(client.getClientId(), codeValiditySeconds);
        redisTemplate.boundValueOps(key(code) + ":scopes")
                .set(String.join(",", scopes), codeValiditySeconds);
    }

    @Override
    public Set<String> getCurrentScopes(String clientId, String code) {
        String find = redisTemplate.boundValueOps(key(code) + ":client_id").get();
        if (null == find || !find.equals(clientId))
            throw new OAuthException("invalid code");
        String strScopes = redisTemplate.boundValueOps(key(code) + ":scopes").get();
        assert null != strScopes;
        return Arrays.stream(strScopes.split(",")).collect(Collectors.toSet());
    }
}
