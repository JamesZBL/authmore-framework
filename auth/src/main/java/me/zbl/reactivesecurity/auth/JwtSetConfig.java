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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An instance of Legacy Authorization Server (spring-security-oauth2) that uses a single,
 * not-rotating key and exposes a JWK endpoint.
 *
 * @author JamesZBL
 * @email 1146556298@qq.com
 * @date 2019-01-25
 */
@Configuration
@EnableAuthorizationServer
public class JwtSetConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private RedisConnectionFactory redisConnectionFactory;
    private KeyPair keyPair;

    public JwtSetConfig(RedisConnectionFactory redisConnectionFactory, KeyPair keyPair, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.redisConnectionFactory = redisConnectionFactory;
        this.keyPair = keyPair;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        // @formatter:off
		clients.inMemory()
			.withClient("userapp")
				.authorizedGrantTypes("password")
				.secret("{pbkdf2}30d47c8ef17066e65750bb6469b951dbaf8b40d4cf4b421490ffff92da00804700c8b8fb92cc9ce0")
				.scopes("user:read")
				.accessTokenValiditySeconds(3600)
				.and()
			.withClient("messageapp")
				.authorizedGrantTypes("client_credentials")
				.secret("{pbkdf2}30d47c8ef17066e65750bb6469b951dbaf8b40d4cf4b421490ffff92da00804700c8b8fb92cc9ce0")
				.scopes("message:read","user:read")
				.accessTokenValiditySeconds(3600);
		// @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // @formatter:off
		endpoints
			.authenticationManager(this.authenticationManager)
			.accessTokenConverter(accessTokenConverter())
			.tokenStore(tokenStore());
		// @formatter:on
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair);

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }

    /**
     * Legacy Authorization Server does not support a custom name for the user parameter, so we'll need
     * to extend the default. By default, it uses the attribute {@code user_name}, though it would be
     * better to adhere to the {@code sub} property defined in the
     * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT Specification</a>.
     */
    static class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {
        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("sub", authentication.getName());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            return response;
        }
    }
}
