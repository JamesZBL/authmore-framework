package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static me.zbl.authmore.main.AuthenticationException.*;
import static me.zbl.authmore.main.OAuthException.INVALID_CLIENT;
import static me.zbl.authmore.main.OAuthException.REDIRECT_URI_MISMATCH;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
@Service
public final class RepoAuthenticationManager implements AuthenticationManager {

    private final UserDetailsRepository users;
    private final ClientDetailsRepository clients;
    private final PasswordEncoder passwordEncoder;

    public RepoAuthenticationManager(
            UserDetailsRepository users, ClientDetailsRepository clients,
            PasswordEncoder passwordEncoder) {
        this.users = users;
        this.clients = clients;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails userValidate(String principal, String credential) throws AuthenticationException {
        Optional<UserDetails> find = users.findByUsername(principal);
        if (!find.isPresent()) {
            throw new AuthenticationException(INVALID_USERNAME);
        }
        UserDetails user = find.get();
        String storedPassword = user.getPassword();
        boolean valid = passwordEncoder.matches(credential, storedPassword);
        boolean enabled = user.isEnabled();
        if (!valid)
            throw new AuthenticationException(INVALID_PASSWORD);
        if (!enabled)
            throw new AuthenticationException(ACCOUNT_DISABLED);
        return user;
    }

    @Override
    public ClientDetails clientValidate(String clientId, String scope) throws OAuthException {
        Optional<ClientDetails> find = clients.findByClientId(clientId);
        if (!find.isPresent())
            throw new OAuthException(INVALID_CLIENT);
        ClientDetails client = find.get();
        OAuthUtil.validateClientAndScope(client, scope);
        return client;
    }

    @Override
    public ClientDetails clientValidate(String clientId, String redirectUri, String scope) throws AuthorizationException {
        Optional<ClientDetails> find = clients.findByClientId(clientId);
        if (!find.isPresent())
            throw new AuthorizationException(INVALID_CLIENT);
        ClientDetails client = find.get();
        Set<String> registeredRedirectUri = client.getRegisteredRedirectUri();
        boolean validRedirectUri = registeredRedirectUri.stream().anyMatch(r -> r.equals(redirectUri));
        if (!validRedirectUri)
            throw new AuthorizationException(REDIRECT_URI_MISMATCH);
        OAuthUtil.validateClientAndScope(client, scope);
        return client;
    }
}
