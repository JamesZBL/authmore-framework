package me.zbl.authmore.main;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
@RedisHash(value = OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING,
           timeToLive = OAuthProperties.DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS)
public final class AccessTokenBinding {

    @Id
    private String accessToken;
    private String clientId;
    private Set<String> scopes;
    private String userId;
    private Long expire;

    public AccessTokenBinding() {}

    public AccessTokenBinding(String accessToken, String clientId, Set<String> scopes, String userId) {
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.scopes = scopes;
        this.userId = userId;
    }

    public AccessTokenBinding(RefreshTokenBinding refreshTokenBinding, String accessToken) {
        this.accessToken = accessToken;
        this.clientId = refreshTokenBinding.getClientId();
        this.scopes = refreshTokenBinding.getScopes();
        this.userId = refreshTokenBinding.getUserId();
    }

    public String getAccessToken() {
        return accessToken;
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

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
