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
package me.zbl.reactivesecurity.auth;

import me.zbl.authmore.admin.AdminApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
public class AdminApplicationTests {

    @Autowired
    private MongoTemplate mongo;

    @Test
    public void contextLoads() {
        mongo.save("{\n" +
                "  \"_id\":ObjectId(\"5cb7e7bcee173c60c379e04e\"),\n" +
                "  \"authorizedGrantTypes\":\"authorization_code,password,implicit,client_credentials\",\n" +
                "  \"scoped\":true,\"scope\":\"PROFILE,EMAIL\",\n" +
                "  \"resourceIds\":\"MAILBOX\",\n" +
                "  \"isSecretRequired\":true,\n" +
                "  \"clientSecret\":\"{pbkdf2}cce0073b0e62e2922fe0e9d145da19dc4f3c63c1af95009fd2d1492ecf8c4a5b84a1a72d6013fb1e\",\n" +
                "  \"authorities\":\"READ\",\n" +
                "  \"registeredRedirectUri\":\"http://localhost:8090/inbox,http://localhost:8090/implicit.html\",\n" +
                "  \"accessTokenValiditySeconds\":99999,\n" +
                "  \"isAutoApprove\":true,\n" +
                "  \"clientName\":\"Mailbox Reader\",\n" +
                "  \"_class\":\"me.zbl.authmore.core.ClientDetails\"\n" +
                "  }", "clientDetails");
    }
}

