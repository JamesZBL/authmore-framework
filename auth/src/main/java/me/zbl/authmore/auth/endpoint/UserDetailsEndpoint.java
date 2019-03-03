package me.zbl.authmore.auth.endpoint;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-29
 */
@RestController
@EnableResourceServer
public class UserDetailsEndpoint {

    @GetMapping("/about/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
