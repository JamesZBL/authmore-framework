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

import java.util.Arrays;

/**
 * @author JamesZBL
 * @since 2019-02-14
 */
interface OAuthProperties {

    long CODE_VALIDITY_SECONDS = 300L;
    long DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS = 300L;
    String SCOPE_USER_DETAILS = "PROFILE";
    String REQUEST_SCOPES = "REQUEST_SCOPES";
    String REQUEST_AUTHORITIES = "REQUEST_AUTHORITIES";
    String KEY_PREFIX_CODE_BINDING = "authmore:authorization:code";
    String KEY_PREFIX_ACCESS_TOKEN_BINDING = "authmore:authorization:access-token";
    String KEY_PREFIX_REFRESH_TOKEN_BINDING = "authmore:authorization:refresh-token";

    enum GrantTypes {

        AUTHORIZATION_CODE("authorization_code"),
        IMPLICIT("implicit"),
        PASSWORD("password"),
        CLIENT_CREDENTIALS("client_credentials"),
        REFRESH_TOKEN("refresh_token");

        private final String name;

        GrantTypes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static GrantTypes eval(String name) {
            return Arrays.stream(GrantTypes.values())
                    .filter(t -> t.getName().equals(name)).findFirst()
                    .orElseThrow(() -> new OAuthException(OAuthException.UNSUPPORTED_GRANT_TYPE));
        }
    }

    enum ResponseTypes {

        CODE("code"),
        TOKEN("token");

        private final String name;

        ResponseTypes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static ResponseTypes eval(String name) {
            return Arrays.stream(ResponseTypes.values())
                    .filter(t -> t.getName().equals(name)).findFirst()
                    .orElseThrow(() -> new OAuthException(OAuthException.UNSUPPORTED_RESPONSE_TYPE));
        }
    }

    enum RequireTypes {

        ALL, ANY
    }
}
