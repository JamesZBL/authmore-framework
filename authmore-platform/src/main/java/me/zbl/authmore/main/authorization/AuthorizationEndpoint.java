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
package me.zbl.authmore.main.authorization;

import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.core.UserDetails;
import me.zbl.authmore.main.oauth.CodeManager;
import me.zbl.authmore.main.oauth.OAuthProperties.ResponseTypes;
import me.zbl.authmore.main.oauth.OAuthUtil;
import me.zbl.authmore.main.oauth.TokenManager;
import me.zbl.authmore.main.oauth.TokenResponse;
import me.zbl.reactivesecurity.common.RandomSecret;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static me.zbl.authmore.main.authorization.SessionProperties.*;
import static me.zbl.authmore.main.oauth.OAuthException.*;
import static me.zbl.authmore.main.oauth.OAuthProperties.GrantTypes.AUTHORIZATION_CODE;
import static me.zbl.authmore.main.oauth.OAuthProperties.GrantTypes.IMPLICIT;
import static me.zbl.authmore.main.oauth.OAuthProperties.ResponseTypes.*;
import static me.zbl.authmore.main.oauth.OAuthUtil.scopeSet;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-14
 */
@Controller
public class AuthorizationEndpoint {

    private final AuthenticationManager authenticationManager;
    private final CodeManager codeManager;
    private final TokenManager tokenManager;

    public AuthorizationEndpoint(
            AuthenticationManager authenticationManager,
            CodeManager codeManager,
            TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.codeManager = codeManager;
        this.tokenManager = tokenManager;
    }

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("client_id") String clientId,
            @RequestParam("response_type") String responseType,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state,
            @SessionAttribute(CURRENT_USER_DETAILS) UserDetails user,
            HttpSession session,
            Model model,
            HttpServletResponse response) throws IOException {
        String location;
        ClientDetails client = authenticationManager.clientValidate(clientId, redirectUri, scope);
        String userId = user.getId();
        ResponseTypes type = eval(responseType);
        Set<String> scopes = scopeSet(scope);
        switch (type) {
            case CODE:
                try {
                    OAuthUtil.validateClientAndGrantType(client, AUTHORIZATION_CODE);
                } catch (Exception e) {
                    throw new AuthorizationException(UNSUPPORTED_GRANT_TYPE);
                }
                if (client.isAutoApprove()) {
                    String code = RandomSecret.create();
                    codeManager.saveCodeBinding(client, code, scopes, redirectUri, userId);
                    location = String.format("%s?code=%s", redirectUri, code);
                    if (null != state)
                        location = String.format("%s&state=%s", location, state);
                    response.sendRedirect(location);
                }
                session.setAttribute(LAST_TYPE, CODE);
                break;
            case TOKEN:
                try {
                    OAuthUtil.validateClientAndGrantType(client, IMPLICIT);
                } catch (Exception e) {
                    throw new AuthorizationException(UNSUPPORTED_GRANT_TYPE);
                }
                if (client.isAutoApprove()) {
                    TokenResponse tokenResponse;
                    try {
                        tokenResponse = tokenManager.create(client, userId, scopes);
                    } catch (Exception e) {
                        throw new AuthorizationException(e.getMessage());
                    }
                    String accessToken = tokenResponse.getAccess_token();
                    location = String.format("%s#access_token=%s", redirectUri, accessToken);
                    if (null != state)
                        location = String.format("%s&state=%s", location, state);
                    response.sendRedirect(location);
                }
                session.setAttribute(LAST_TYPE, TOKEN);
                break;
            default:
                throw new AuthorizationException(UNSUPPORTED_RESPONSE_TYPE);
        }
        session.setAttribute(CURRENT_REDIRECT_URI, redirectUri);
        session.setAttribute(CURRENT_CLIENT, client);
        session.setAttribute(LAST_SCOPE, scope);
        model.addAttribute("client", client);
        if (!isEmpty(state)) {
            session.setAttribute(LAST_STATE, state);
        }
        return "authorize";
    }

    @PostMapping("/authorize/confirm")
    public void authorizeConfirm(
            @RequestParam("client_id") String clientId,
            @RequestParam("opinion") String opinion,
            @SessionAttribute(CURRENT_USER_DETAILS) UserDetails user,
            @SessionAttribute(CURRENT_CLIENT) ClientDetails client,
            @SessionAttribute(CURRENT_REDIRECT_URI) String redirectUri,
            @SessionAttribute(LAST_SCOPE) String scope,
            @SessionAttribute(LAST_STATE) String state,
            @SessionAttribute(LAST_TYPE) ResponseTypes type,
            HttpServletResponse response) throws IOException {
        String location;
        if (null == client || !client.getClientId().equals(clientId)) {
            throw new AuthorizationException(INVALID_CLIENT);
        }
        if (!"allow".equals(opinion)) {
            throw new AuthorizationException("signin was rejected");
        }
        String userId = user.getId();
        Set<String> scopes = scopeSet(scope);
        switch (type) {
            case CODE:
                String code = RandomSecret.create();
                codeManager.saveCodeBinding(client, code, scopes, redirectUri, userId);
                location = String.format("%s?code=%s", redirectUri, code);
                if (null != state)
                    location = String.format("%s&state=%s", location, state);
                response.sendRedirect(location);
                break;
            case TOKEN:
                TokenResponse tokenResponse;
                try {
                    tokenResponse = tokenManager.create(client, userId, scopes);
                } catch (Exception e) {
                    throw new AuthorizationException(e.getMessage());
                }
                String accessToken = tokenResponse.getAccess_token();
                location = String.format("%s#token=%s", redirectUri, accessToken);
                if (null != state)
                    location = String.format("%s&state=%s", location, state);
                response.sendRedirect(location);
                break;
            default:
                throw new AuthorizationException(UNSUPPORTED_RESPONSE_TYPE);
        }
    }
}
