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
package me.zbl.reactivesecurity.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author JamesZBL
 * @email 1146556298@qq.com
 * @date 2019-01-28
 */
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientsConfig {

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * @author JamesZBL
     * @email 1146556298@qq.com
     * @date 2019-01-28
     */
    public static class Client {

        private String id;
        private String secret;
        private String scopes;
        private String grantTypes;
        private Integer accessTokenValiditySeconds;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getScopes() {
            return scopes;
        }

        public void setScopes(String scopes) {
            this.scopes = scopes;
        }

        public String getGrantTypes() {
            return grantTypes;
        }

        public void setGrantTypes(String grantTypes) {
            this.grantTypes = grantTypes;
        }

        public Integer getAccessTokenValiditySeconds() {
            return accessTokenValiditySeconds;
        }

        public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
            this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        }
    }
}
