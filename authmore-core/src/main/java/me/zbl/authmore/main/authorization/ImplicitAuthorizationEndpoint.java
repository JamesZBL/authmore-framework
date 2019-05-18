package me.zbl.authmore.main.authorization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-17
 */
@Controller
public class ImplicitAuthorizationEndpoint {

    @GetMapping("/implicit.html")
    public String view() {
        return "implicit";
    }
}
