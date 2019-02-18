/*
 * Copyright 2019 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.client.ClientDetails;
import me.zbl.reactivesecurity.auth.user.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static me.zbl.authmore.AuthenticationException.*;
import static me.zbl.authmore.OAuthException.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-15
 */
@Service
public class RepoAuthenticationManager implements AuthenticationManager {

    private UserDetailsRepository users;
    private ClientDetailsRepository clients;
    private PasswordEncoder passwordEncoder;

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
    public ClientDetails clientValidate(String clientId, String redirectUri, String scope) throws OAuthException {
        Optional<ClientDetails> find = clients.findByClientId(clientId);
        if (!find.isPresent())
            throw new OAuthException(INVALID_CLIENT);
        ClientDetails client = find.get();
        Set<String> registeredRedirectUri = client.getRegisteredRedirectUri();
        boolean validRedirectUri = registeredRedirectUri.stream().anyMatch(r -> r.equals(redirectUri));
        if (!validRedirectUri)
            throw new OAuthException(REDIRECT_URI_MISMATCH);
        if (!isEmpty(scope)) {
            Set<String> registeredScope = client.getScope();
            boolean validScope = Arrays.stream(scope.split("\\+"))
                    .allMatch(s -> registeredScope.contains(scope));
            if (!validScope)
                throw new OAuthException(INVALID_SCOPE);
        }
        return client;
    }
}
