package me.zbl.authmore.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * endpoints for test security config
 *
 * @author ZHENG BAO LE
 * @since 2019-02-13
 */
@RestController
@RequestMapping("/test")
public class TestEndpoint {

    @GetMapping()
    public String test() {
        return "testing success";
    }
}
