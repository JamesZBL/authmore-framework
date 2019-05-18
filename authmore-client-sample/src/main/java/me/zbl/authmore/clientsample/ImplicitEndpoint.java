package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.client.ClientRestTemplate;
import me.zbl.authmore.main.oauth.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-18
 */
@RestController
public class ImplicitEndpoint {

    @PostMapping("/token")
    public Object token(@RequestBody TokenResponse tokenResponse) {
        String token = tokenResponse.getAccess_token();
        ClientRestTemplate restTemplate = new ClientRestTemplate(token);
        return restTemplate.getForObject("http://localhost:8091/inbox", Inbox.class);
    }
}
