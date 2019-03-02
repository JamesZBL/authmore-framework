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
package me.zbl.authmore.configuration;

import me.zbl.authmore.main.ClientConfigurationProperties;
import me.zbl.authmore.main.ClientHttpClientInterceptor;
import me.zbl.authmore.main.ClientRestTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_ID;
import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_SECRET;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-03-01
 */
@Configuration
@ConditionalOnClass({ClientHttpClientInterceptor.class})
@EnableConfigurationProperties({ClientConfigurationProperties.class})
public class ClientAutoConfiguration implements SmartInitializingSingleton {

    private final String clientId;
    private final String clientSecret;

    public ClientAutoConfiguration(ClientConfigurationProperties clientConfigurationProperties) {
        this.clientId = clientConfigurationProperties.getClientId();
        this.clientSecret = clientConfigurationProperties.getClientSecret();
    }

    @Bean
    public ClientRestTemplate restTemplate() {
        ClientRestTemplate template = new ClientRestTemplate();
        ClientHttpClientInterceptor interceptor = new ClientHttpClientInterceptor(clientId, clientSecret);
        template.setInterceptors(Stream.of(interceptor).collect(Collectors.toList()));
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_CLIENT_ID, clientId);
        params.put(PARAM_CLIENT_SECRET, clientSecret);
        template.setDefaultUriVariables(params);
        return template;
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (isEmpty(clientId) || isEmpty(clientSecret))
            throw new IllegalStateException("oauth client must specify a client-id and client-secret");
    }
}
