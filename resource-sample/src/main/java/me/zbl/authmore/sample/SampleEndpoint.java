package me.zbl.authmore.sample;

import me.zbl.authmore.main.AuthorityRequired;
import me.zbl.authmore.main.ScopeRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
@RestController
public class SampleEndpoint {

    @GetMapping()
    @ScopeRequired("PROFILE")
    @AuthorityRequired("SA")
    public String sample() {
        return "sample";
    }
}
