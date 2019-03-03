package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-26
 */
@RestController
public class TokenCheckEndpoint {

    private final TokenManager tokenManager;
    private final UserDetailsRepository users;
    private final ClientDetailsRepository clients;

    public TokenCheckEndpoint(TokenManager tokenManager, UserDetailsRepository users, ClientDetailsRepository clients) {
        this.tokenManager = tokenManager;
        this.users = users;
        this.clients = clients;
    }

    @GetMapping("/oauth/check_token")
    public TokenCheckResponse checkToken(@RequestParam(value = "token", required = false) String token) {
        AccessTokenBinding accessTokenBinding = tokenManager.findAccessToken(token);
        String userId = accessTokenBinding.getUserId();
        String clientId = accessTokenBinding.getClientId();
        Set<String> authorities = new HashSet<>();
        if (null != userId) {
            UserDetails user = users.findById(userId).orElseThrow(() -> new OAuthException("no such user"));
            authorities.addAll(user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        }
        ClientDetails client = clients.findByClientId(clientId).orElseThrow(() -> new OAuthException("no such client"));
        authorities.addAll(
                client.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        return new TokenCheckResponse(accessTokenBinding, authorities);
    }
}
