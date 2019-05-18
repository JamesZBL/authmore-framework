package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.client.ClientRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-18
 */
@RestController
public class ClientCredentialsEndpoint {

    private final ClientRestTemplate grantedClient;

    @Autowired
    public ClientCredentialsEndpoint(ClientRestTemplate grantedClient) {
        this.grantedClient = grantedClient;
    }

    @GetMapping("/client")
    public String clientCredentials() {
        return this.grantedClient.getForObject("http://localhost:8091/inbox", String.class);
    }
}
