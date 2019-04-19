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
package me.zbl.authmore.auth.config;

import me.zbl.authmore.core.PasswordEncoderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * For configuring the end users recognized by this Authorization Server
 *
 * @author ZHENG BAO LE
 * @since 2019-01-25
 */
@Order(1)
@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactory.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        /* until now stable */
        // @formatter:off
        http.csrf().disable()
            .authorizeRequests()
            .mvcMatchers("/auth/jwk").permitAll()
            .and()
            .requestMatchers().antMatchers("/login","/oauth/authorize")
            .and()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin();
        // @formatter:on
        // @formatter:on
        /* original stable edition */
        //    http.csrf().disable()
        //    .authorizeRequests()
        //    .mvcMatchers("/auth/jwk").permitAll()
        //    .and()
        //    .requestMatchers().antMatchers("/login","/oauth/authorize")
        //    .and()
        //    .authorizeRequests().anyRequest().authenticated()
        //    .and()
        //    .formLogin();
        // @formatter:on
    }
}
