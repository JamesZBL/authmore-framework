package me.zbl.authmore.main;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
@RedisHash(value = OAuthProperties.KEY_PREFIX_CODE_BINDING, timeToLive = OAuthProperties.CODE_VALIDITY_SECONDS)
public final class CodeBinding implements Serializable {

    @Id
    private final String code;
    private final String clientId;
    private final Set<String> scopes;
    private final String redirectUri;
    private final String userId;

    public CodeBinding(String code, String clientId, Set<String> scopes, String redirectUri, String userId) {
        this.code = code;
        this.clientId = clientId;
        this.scopes = scopes;
        this.redirectUri = redirectUri;
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getUserId() {
        return userId;
    }

}
