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
package me.zbl.authmore.admin.client;

import me.zbl.authmore.ClientDetails;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Component
public class ClientInitializer implements SmartInitializingSingleton {

    private static final String ROOT_APP_ID = "5cb0dd412dc963313f1a90b1";
    private ClientDetailsRepo clientDetailsRepo;

    public ClientInitializer(ClientDetailsRepo clientDetailsRepo) {
        this.clientDetailsRepo = clientDetailsRepo;
    }

    @Override
    public void afterSingletonsInstantiated() {
        ClientDetails client = new ClientDetails(ROOT_APP_ID,
                "authorization_code,password,implicit,client_credentials,refresh_token", "PROFILE",
                "{pbkdf2}30d47c8ef17066e65750bb6469b951dbaf8b40d4cf4b421490ffff92da00804700c8b8fb92cc9ce0",
                "", 999999,
                "SAMPLE", "SA");
        client.setClientName("平台管理");
        clientDetailsRepo.save(client);
    }
}
