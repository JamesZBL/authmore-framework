/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zbl.authmore.main.oauth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.authorization.RequestProperties;
import me.zbl.authmore.main.oauth.OAuthProperties.GrantTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
@Api(description = "令牌签发")
@RestController
public class TokenEndpoint {

    private final TokenAuthorizationCodeTokenIssuer authorizationCodeTokenIssuer;
    private final TokenPasswordTokenIssuer passwordTokenIssuer;
    private final TokenClientCredentialsTokenIssuer clientCredentialsTokenIssuer;
    private final TokenRefreshTokenIssuer refreshTokenIssuer;

    public TokenEndpoint(
            TokenAuthorizationCodeTokenIssuer authorizationCodeTokenIssuer,
            TokenPasswordTokenIssuer passwordTokenIssuer,
            TokenClientCredentialsTokenIssuer clientCredentialsTokenIssuer,
            TokenRefreshTokenIssuer refreshTokenIssuer) {
        this.authorizationCodeTokenIssuer = authorizationCodeTokenIssuer;
        this.passwordTokenIssuer = passwordTokenIssuer;
        this.clientCredentialsTokenIssuer = clientCredentialsTokenIssuer;
        this.refreshTokenIssuer = refreshTokenIssuer;
    }

    @ApiOperation("令牌签发")
    @PostMapping("/oauth/token")
    public TokenResponse token(
            @ApiParam("授权方式") @RequestParam(value = "grant_type", required = false) String grantType,
            @ApiParam("授权码") @RequestParam(value = "code", required = false) String code,
            @ApiParam("回调地址") @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @ApiParam("客户端 AppId") @RequestParam(value = "client_id", required = false) String clientId,
            @ApiParam("用户名") @RequestParam(value = "username", required = false) String username,
            @ApiParam("用户密码") @RequestParam(value = "password", required = false) String password,
            @ApiParam("授权范围") @RequestParam(value = "scope", required = false) String scope,
            @ApiParam("刷新令牌") @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @ApiIgnore @RequestAttribute(RequestProperties.CURRENT_CLIENT) ClientDetails client) {
        GrantTypes realType = GrantTypes.eval(grantType);
        if (isEmpty(clientId))
            throw new OAuthException(OAuthException.INVALID_CLIENT);
        switch (realType) {
            case AUTHORIZATION_CODE:
                return authorizationCodeTokenIssuer.issue(client, redirectUri, code);
            case PASSWORD:
                return passwordTokenIssuer.issue(client, username, password, scope);
            case CLIENT_CREDENTIALS:
                return clientCredentialsTokenIssuer.issue(client, scope);
            case REFRESH_TOKEN:
                return refreshTokenIssuer.issue(client, refreshToken);
            default:
                throw new OAuthException(OAuthException.UNSUPPORTED_GRANT_TYPE);
        }
    }
}
