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

import me.zbl.authmore.main.client.ClientRestTemplate;
import me.zbl.authmore.main.oauth.PasswordTokenManager;
import me.zbl.authmore.main.oauth.TokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
@RestController
public class SampleEndpoint {

    private final PasswordTokenManager passwordTokenManager;
    private final ClientRestTemplate grantedClient;

    public SampleEndpoint(
            PasswordTokenManager passwordTokenManager,
            ClientRestTemplate grantedClient) {
        this.passwordTokenManager = passwordTokenManager;
        this.grantedClient = grantedClient;
    }

    @GetMapping
    public String password() {
        Map<String, String> params = new HashMap<>();
        params.put("scope", "PROFILE");
        params.put("username", "james");
        params.put("password", "123456");
        TokenResponse token = passwordTokenManager.getToken("PROFILE", params);
        RestTemplate template = new ClientRestTemplate(token.getAccess_token());
        return template.getForObject("http://localhost:8011/", String.class);
    }

    @GetMapping
    public String clientCredentials() {
        return this.grantedClient.getForObject("http://localhost:8011/", String.class);
    }
}
