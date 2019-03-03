package me.zbl.authmore.main;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-26
 */
@RedisHash(value = OAuthProperties.KEY_PREFIX_REFRESH_TOKEN_BINDING)
public final class RefreshTokenBinding {

    @Id
    private final String refreshToken;
    private final String clientId;
    private final Set<String> scopes;
    private final String userId;

    public RefreshTokenBinding(String refreshToken, String clientId, Set<String> scopes, String userId) {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.scopes = scopes;
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getUserId() {
        return userId;
    }
}
