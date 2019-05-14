package me.zbl.authmore.sample;

import me.zbl.authmore.main.resource.AuthorityRequired;
import me.zbl.authmore.main.resource.ScopeRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
@RestController
public class InboxResourceEndpoint {

    @GetMapping("/inbox")
    @ScopeRequired("EMAIL")
    @AuthorityRequired("READ")
    public Inbox inbox() {
        return new Inbox(Arrays.asList(
                new Email().setFrom("James").setTo("Tom").setContent("Hello, Tom!"),
                new Email().setFrom("Tom").setTo("James").setContent("Hi, James!"),
                new Email().setFrom("Tony").setTo("James").setContent("James, Let's go hiking!")));
    }
}
