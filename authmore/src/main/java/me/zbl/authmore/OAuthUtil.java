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

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static me.zbl.authmore.OAuthException.INVALID_SCOPE;
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

    public static boolean validateClient(ClientDetails client, String scope) {
        if (!isEmpty(scope)) {
            Set<String> registeredScope = client.getScope();
            boolean validScope = Arrays.stream(scope.split("\\+"))
                    .allMatch(s -> registeredScope.contains(scope));
            if (!validScope)
                throw new OAuthException(INVALID_SCOPE);
        }
        return true;
    }
}
