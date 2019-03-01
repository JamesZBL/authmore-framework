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
import me.zbl.authmore.main.OAuthProperties.GrantTypes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static me.zbl.authmore.main.OAuthException.INVALID_SCOPE;
import static me.zbl.authmore.main.OAuthException.UNSUPPORTED_GRANT_TYPE;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-19
 */
public class OAuthUtil {

    private static final String SCOPE_DELIMITER = "\\+";
    private static final String AUTHORITY_DELIMITER = ",";

    public static Set<String> scopeSet(String scope) {
        if (isEmpty(scope))
            return Collections.emptySet();
        return Arrays.stream(scope.split(SCOPE_DELIMITER)).collect(Collectors.toSet());
    }

    public static Set<String> authoritySet(String authority) {
        if (isEmpty(authority))
            return Collections.emptySet();
        return Arrays.stream(authority.split(AUTHORITY_DELIMITER)).collect(Collectors.toSet());
    }

    public static void validateClientAndScope(ClientDetails client, String scope) {
        validateClientAndScope(client, scope, true);
    }

    public static void validateClientAndScope(ClientDetails client, String scope, boolean notEmptyScope) {
        if (!isEmpty(scope)) {
            Set<String> registeredScope = client.getScope();
            boolean validScope = Arrays.stream(scope.split("\\+"))
                    .allMatch(s -> registeredScope.contains(scope));
            if (!validScope)
                throw new OAuthException(INVALID_SCOPE);
        } else {
            if (notEmptyScope)
                throw new OAuthException(INVALID_SCOPE);
        }
    }

    public static void validateClientAndGrantType(ClientDetails client, GrantTypes grantType) {
        boolean valid;
        if (!isEmpty(grantType))
            valid = client.getAuthorizedGrantTypes().contains(grantType.getName());
        else
            valid = false;
        if (!valid)
            throw new OAuthException(UNSUPPORTED_GRANT_TYPE);
    }

    public static long expireAtByLiveTime(long expireIn) {
        return System.currentTimeMillis() / 1000 + expireIn;
    }

    public static boolean support(OAuthProperties.RequireTypes type, String[] required, Set<String> exist) {
        switch (type) {
            case ANY:
                return Arrays.stream(required).allMatch(exist::contains);
            case ALL:
                return Arrays.stream(required).anyMatch(exist::contains);
            default:
                return false;
        }
    }

    public static String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (isEmpty(authorization) || !authorization.startsWith("Bearer")) {
            throw new OAuthException("invalid token");
        }
        String[] words = authorization.split(" ");
        if (2 > words.length) {
            throw new OAuthException("invalid token");
        }
        return words[1];
    }
}