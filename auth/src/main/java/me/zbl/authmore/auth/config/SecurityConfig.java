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
@EnableWebSecurity(debug = true)
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
