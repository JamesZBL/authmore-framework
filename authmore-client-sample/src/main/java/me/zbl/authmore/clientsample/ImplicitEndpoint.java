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

import me.zbl.authmore.client.AuthorizationTemplate;
import me.zbl.authmore.client.ClientRestTemplate;
import me.zbl.authmore.oauth.OAuthProperties;
import me.zbl.authmore.oauth.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-18
 */
@RestController
public class ImplicitEndpoint {

    private final AuthorizationTemplate authorizationTemplate;
    private static final String SCOPES = "PROFILE+EMAIL";

    @Autowired
    public ImplicitEndpoint(AuthorizationTemplate authorizationTemplate) {
        this.authorizationTemplate = authorizationTemplate;
    }

    @GetMapping("/implicit")
    public void implicit(HttpServletResponse response) throws IOException {
        authorizationTemplate.redirectToUserAuthorize(response, OAuthProperties.ResponseTypes.TOKEN, SCOPES);
    }

    @PostMapping(value = "/token", produces = {"application/json"})
    public Object token(@RequestBody TokenResponse tokenResponse) {
        String token = tokenResponse.getAccess_token();
        ClientRestTemplate restTemplate = new ClientRestTemplate(token);
        return restTemplate.getForObject("http://resource.authmore/inbox", String.class);
    }
}
