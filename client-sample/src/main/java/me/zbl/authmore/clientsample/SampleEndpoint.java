package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.ClientPasswordTokenManager;
import me.zbl.authmore.main.ClientRestTemplate;
import me.zbl.authmore.main.TokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
@RestController
public class SampleEndpoint {

    private final ClientPasswordTokenManager passwordTokenManager;

    public SampleEndpoint(ClientPasswordTokenManager passwordTokenManager) {
        this.passwordTokenManager = passwordTokenManager;
    }

    @GetMapping
    public String password() {
        Map<String, String> params = new HashMap<>();
        params.put("scope", "PROFILE");
        params.put("username", "james");
        params.put("password", "123456");
        TokenResponse token = passwordTokenManager.getToken("PROFILE", params);
        RestTemplate template = new ClientRestTemplate(token.getAccess_token());
        return template.getForObject("http://localhost:8011/", String.class);
    }
}
