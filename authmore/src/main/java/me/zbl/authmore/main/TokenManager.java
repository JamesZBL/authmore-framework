package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
public interface TokenManager {

    TokenResponse create(ClientDetails client, String userId, Set<String> scopes);

    TokenResponse refresh(String refreshToken);

    AccessTokenBinding findAccessToken(String token);

    RefreshTokenBinding findRefreshToken(String token);

    RefreshTokenBinding freshRefreshTokenBinding(ClientDetails client, RefreshTokenBinding refreshTokenBinding);

    void saveAccessToken(AccessTokenBinding accessTokenBinding);

    void saveRefreshToken(RefreshTokenBinding refreshTokenBinding);

    void expireAccessToken(String token, long expireIn);

    void expireRefreshToken(String token, long expireIn);
}
