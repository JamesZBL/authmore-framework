package me.zbl.authmore.main.authorization;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-17
 */
@FrameworkEndpoint
public class ImplicitTokenResolver {

    @GetMapping("/implicit.html")
    public String view() {
        return "implicit";
    }
}
