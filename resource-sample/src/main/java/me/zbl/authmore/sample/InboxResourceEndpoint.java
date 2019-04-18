package me.zbl.authmore.sample;

import me.zbl.authmore.main.resource.ScopeRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
@RestController
public class InboxResourceEndpoint {

    @GetMapping("/inbox")
    @ScopeRequired("EMAIL")
    public Inbox inbox() {
        return new Inbox(Collections.emptyList());
    }
}
