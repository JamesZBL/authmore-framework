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

package me.zbl.authmore.clientsample;

import me.zbl.authmore.client.AuthorizationCodeTokenManager;
import me.zbl.authmore.client.AuthorizationTemplate;
import me.zbl.authmore.client.ClientRestTemplate;
import me.zbl.authmore.oauth.OAuthProperties;
import me.zbl.authmore.oauth.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
@RestController
public class CodeEndpoint {

    private AuthorizationCodeTokenManager tokenManager;
    private AuthorizationTemplate authorizationTemplate;
    private static final String SCOPES = "PROFILE+EMAIL";

    @Autowired
    public CodeEndpoint(
            AuthorizationCodeTokenManager tokenManager,
            AuthorizationTemplate authorizationTemplate) {
        this.tokenManager = tokenManager;
        this.authorizationTemplate = authorizationTemplate;
    }

    @GetMapping("/inbox")
    public Object inbox(
            @RequestParam(value = "code", required = false) String code,
            HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(code)) {
            authorizationTemplate.redirectToUserAuthorize(
                    response, OAuthProperties.ResponseTypes.CODE, SCOPES);
            return null;
        }
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        TokenResponse token = tokenManager.getToken(SCOPES, params);
        ClientRestTemplate restTemplate =
                new ClientRestTemplate(token.getAccess_token());
        return restTemplate.getForObject("http://localhost:8091/inbox", Inbox.class);
    }
}
