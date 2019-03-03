package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
public interface AuthenticationManager {

    UserDetails userValidate(String principal, String credential) throws AuthenticationException;

    ClientDetails clientValidate(String clientId, String scope) throws OAuthException;

    ClientDetails clientValidate(String clientId, String redirectUri, String scope) throws AuthorizationException;
}
