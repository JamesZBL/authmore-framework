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

import me.zbl.authmore.client.ClientRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-18
 */
@RestController
public class ClientCredentialsEndpoint {

    private final ClientRestTemplate grantedClient;

    @Autowired
    public ClientCredentialsEndpoint(ClientRestTemplate grantedClient) {
        this.grantedClient = grantedClient;
    }

    @GetMapping(value = "/client", produces = {"application/json"})
    public Object clientCredentials() {
        return this.grantedClient.getForObject("http://127.0.0.1:8091/inbox", String.class);
    }
}
