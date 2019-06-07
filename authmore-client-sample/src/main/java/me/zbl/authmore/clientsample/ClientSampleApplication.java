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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample application for authmore-client-springboot-starter
 * <p>
 * Document for testing:
 * <p>
 * {
 * "_id":"5cb7e7bcee173c60c379e04e",
 * "authorizedGrantTypes":"authorization_code,password,implicit,client_credentials",
 * "scoped":true,"scope":"PROFILE,EMAIL",
 * "resourceIds":"MAILBOX",
 * "isSecretRequired":true,
 * "clientSecret":"{pbkdf2}cce0073b0e62e2922fe0e9d145da19dc4f3c63c1af95009fd2d1492ecf8c4a5b84a1a72d6013fb1e",
 * "authorities":"READ",
 * "registeredRedirectUri":"http://client.authmore/inbox,http://client.authmore/implicit.html",
 * "accessTokenValiditySeconds":99999,
 * "isAutoApprove":true,
 * "clientName":"Mailbox Reader",
 * "_class":"me.zbl.authmore.core.ClientDetails"
 * }
 * </p>
 */
@SpringBootApplication
public class ClientSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientSampleApplication.class, args);
    }
}
