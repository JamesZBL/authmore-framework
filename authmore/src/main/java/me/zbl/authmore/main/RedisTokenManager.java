package me.zbl.authmore.main;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static me.zbl.authmore.main.OAuthException.INVALID_TOKEN;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
public class RedisTokenManager extends AbstractTokenManager {

    private final AccessTokenRepository tokens;
    private final RefreshTokenRepository refreshTokens;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisTokenManager(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate,
            ClientDetailsRepository clients) {
        super(clients);
        this.tokens = tokens;
        this.refreshTokens = refreshTokens;
        this.redisTemplate = redisTemplate;
    }

    private void expireToken(String keyPrefix, String token, long expireIn) {
        redisTemplate.boundHashOps(keyPrefix + ":" + token).expire(expireIn, TimeUnit.SECONDS);
    }

    @Override
    public AccessTokenBinding findAccessToken(String token) {
        return tokens.findById(token).orElseThrow(() -> new OAuthException(INVALID_TOKEN));
    }

    @Override
    public RefreshTokenBinding findRefreshToken(String token) {
        return refreshTokens.findById(token).orElseThrow(() -> new OAuthException(INVALID_TOKEN));
    }

    @Override
    public void saveAccessToken(AccessTokenBinding accessTokenBinding) {
        tokens.save(accessTokenBinding);
    }

    @Override
    public void saveRefreshToken(RefreshTokenBinding refreshTokenBinding) {
        refreshTokens.save(refreshTokenBinding);
    }

    @Override
    public void expireAccessToken(String token, long expireIn) {
        expireToken(OAuthProperties.KEY_PREFIX_ACCESS_TOKEN_BINDING, token, expireIn);
    }

    @Override
    public void expireRefreshToken(String token, long expireIn) {
        expireToken(OAuthProperties.KEY_PREFIX_REFRESH_TOKEN_BINDING, token, expireIn);
    }
}
