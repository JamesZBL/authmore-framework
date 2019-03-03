package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

import static me.zbl.authmore.main.OAuthProperties.GrantTypes.PASSWORD;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Component
final class TokenPasswordTokenIssuer {

    private final UserDetailsRepository users;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public TokenPasswordTokenIssuer(
            UserDetailsRepository users,
            PasswordEncoder passwordEncoder,
            TokenManager tokenManager) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
    }

    TokenResponse issue(ClientDetails client, String username, String password, String scope) {
        OAuthUtil.validateClientAndGrantType(client, PASSWORD);
        UserDetails user = users.findByUsername(username)
                .orElseThrow(() -> new OAuthException("invalid username"));
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches)
            throw new OAuthException("invalid password");
        OAuthUtil.validateClientAndScope(client, scope);
        String userId = user.getId();
        Set<String> scopes = OAuthUtil.scopeSet(scope);
        return tokenManager.create(client, userId, scopes);
    }
}
