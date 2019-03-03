package me.zbl.authmore.auth;

import me.zbl.reactivesecurity.common.BasicController;
import me.zbl.reactivesecurity.common.RandomSecret;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-11
 */
@RestController
@RequestMapping("/password")
public class PasswordEndpoint extends BasicController {

    @GetMapping("/random")
    public Map randomPassword() {
        return map().put("result", RandomSecret.create()).map();
    }
}

