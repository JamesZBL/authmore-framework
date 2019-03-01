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

import me.zbl.authmore.main.ResourceServerConfigurationProperties;
import me.zbl.authmore.main.ResourceServerFilter;
import me.zbl.authmore.main.ResourceServerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-28
 */
@Configuration
@ConditionalOnClass({ResourceServerFilter.class})
@EnableConfigurationProperties({ResourceServerConfigurationProperties.class})
public class ResourceServerAutoConfiguration implements WebMvcConfigurer, SmartInitializingSingleton {

    private final ResourceServerConfigurationProperties resourceProperties;

    public ResourceServerAutoConfiguration(ResourceServerConfigurationProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceServerInterceptor(resourceProperties));
    }

    @Bean
    public ResourceServerFilter oAuthResourceServerFilter() {
        return new ResourceServerFilter(resourceProperties);
    }

    @Override
    public void afterSingletonsInstantiated() {
        String tokenInfoUrl = resourceProperties.getTokenInfoUrl();
        String clientId = resourceProperties.getClientId();
        String clientSecret = resourceProperties.getClientSecret();
        if (!isEmpty(tokenInfoUrl)) {
            if (isEmpty(clientId) || isEmpty(clientSecret))
                throw new IllegalStateException("If token-info-url value is specified, a client-id " +
                        "and a client-secret is also required.");
        }
    }
}
