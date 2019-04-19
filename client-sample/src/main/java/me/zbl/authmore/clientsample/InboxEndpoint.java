package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.client.AuthorizationCodeTokenManager;
import me.zbl.authmore.main.client.AuthorizationTemplate;
import me.zbl.authmore.main.client.ClientRestTemplate;
import me.zbl.authmore.main.oauth.OAuthProperties;
import me.zbl.authmore.main.oauth.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
@RestController
public class InboxEndpoint {

    private AuthorizationCodeTokenManager tokenManager;
    private AuthorizationTemplate authorizationTemplate;
    private static final String SCOPES = "PROFILE,EMAIL";

    @Autowired
    public InboxEndpoint(
            AuthorizationCodeTokenManager tokenManager,
            AuthorizationTemplate authorizationTemplate) {
        this.tokenManager = tokenManager;
        this.authorizationTemplate = authorizationTemplate;
    }

    @GetMapping("/inbox")
    public Object inbox(
            @RequestParam(value = "code", required = false) String code,
            HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(code)) {
            authorizationTemplate.redirectToUserAuthorize(
                    response, OAuthProperties.ResponseTypes.CODE, SCOPES);
            return null;
        }
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        TokenResponse token = tokenManager.getToken(SCOPES, params);
        ClientRestTemplate restTemplate =
                new ClientRestTemplate(token.getAccess_token());
        return restTemplate.getForObject("http://localhost:8091", Inbox.class);
    }
}
