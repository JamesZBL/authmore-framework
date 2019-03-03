package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.OAuthProperties.GrantTypes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static me.zbl.authmore.main.OAuthException.INVALID_SCOPE;
import static me.zbl.authmore.main.OAuthException.UNSUPPORTED_GRANT_TYPE;
import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_ID;
import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_SECRET;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
public final class OAuthUtil {

    private static final String SCOPE_DELIMITER = "\\+";
    private static final String AUTHORITY_DELIMITER = ",";

    private OAuthUtil() {}

    public static Set<String> scopeSet(String scope) {
        if (isEmpty(scope))
            return Collections.emptySet();
        return Arrays.stream(scope.split(SCOPE_DELIMITER)).collect(Collectors.toSet());
    }

    public static Set<String> authoritySet(String authority) {
        if (isEmpty(authority))
            return Collections.emptySet();
        return Arrays.stream(authority.split(AUTHORITY_DELIMITER)).collect(Collectors.toSet());
    }

    public static void validateClientAndScope(ClientDetails client, String scope) {
        validateClientAndScope(client, scope, true);
    }

    public static void validateClientAndScope(ClientDetails client, String scope, boolean notEmptyScope) {
        if (!isEmpty(scope)) {
            Set<String> registeredScope = client.getScope();
            boolean validScope = Arrays.stream(scope.split("\\+"))
                    .allMatch(s -> registeredScope.contains(scope));
            if (!validScope)
                throw new OAuthException(INVALID_SCOPE);
        } else {
            if (notEmptyScope)
                throw new OAuthException(INVALID_SCOPE);
        }
    }

    public static void validateClientAndGrantType(ClientDetails client, GrantTypes grantType) {
        boolean valid;
        if (!isEmpty(grantType))
            valid = client.getAuthorizedGrantTypes().contains(grantType.getName());
        else
            valid = false;
        if (!valid)
            throw new OAuthException(UNSUPPORTED_GRANT_TYPE);
    }

    public static long expireAtByLiveTime(long expireIn) {
        return System.currentTimeMillis() / 1000 + expireIn;
    }

    public static boolean support(OAuthProperties.RequireTypes type, String[] required, Set<String> exist) {
        if (required.length < 1)
            return true;
        switch (type) {
            case ANY:
                return Arrays.stream(required).allMatch(exist::contains);
            case ALL:
                return Arrays.stream(required).anyMatch(exist::contains);
            default:
                return false;
        }
    }

    public static String extractToken(HttpServletRequest request) {
        return extractAuthorizationFrom(request, "Bearer");
    }

    public static Map<String, String> extractClientFrom(HttpServletRequest request) {
        String basic = extractAuthorizationFrom(request, "Basic");
        byte[] decode = Base64.getDecoder().decode(basic);
        String principle = new String(decode);
        String[] values = principle.split(":");
        if (2 > values.length)
            throw new OAuthException("invalid credentials");
        String clientId = values[0];
        String clientSecret = values[1];
        Map<String, String> result = new HashMap<>();
        result.put(PARAM_CLIENT_ID, clientId);
        result.put(PARAM_CLIENT_SECRET, clientSecret);
        return result;
    }

    public static String extractAuthorizationFrom(HttpServletRequest request, String prefix) {
        String authorization = request.getHeader("Authorization");
        if (isEmpty(authorization) || !authorization.startsWith(prefix)) {
            throw new OAuthException("invalid token");
        }
        String[] words = authorization.split(" ");
        if (2 > words.length) {
            throw new OAuthException("invalid token");
        }
        return words[1];
    }
}
