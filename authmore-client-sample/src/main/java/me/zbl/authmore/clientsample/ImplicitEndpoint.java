package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.client.AuthorizationTemplate;
import me.zbl.authmore.main.client.ClientRestTemplate;
import me.zbl.authmore.main.oauth.OAuthProperties;
import me.zbl.authmore.main.oauth.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-18
 */
@RestController
public class ImplicitEndpoint {

    private final AuthorizationTemplate authorizationTemplate;
    private static final String SCOPES = "PROFILE,EMAIL";

    @Autowired
    public ImplicitEndpoint(AuthorizationTemplate authorizationTemplate) {
        this.authorizationTemplate = authorizationTemplate;
    }

    @GetMapping("/implicit")
    public void implicit(HttpServletResponse response) throws IOException {
        authorizationTemplate.redirectToUserAuthorize(response, OAuthProperties.ResponseTypes.TOKEN, SCOPES);
    }

    @PostMapping("/token")
    public Object token(@RequestBody TokenResponse tokenResponse) {
        String token = tokenResponse.getAccess_token();
        ClientRestTemplate restTemplate = new ClientRestTemplate(token);
        return restTemplate.getForObject("http://localhost:8091/inbox", Inbox.class);
    }
}
