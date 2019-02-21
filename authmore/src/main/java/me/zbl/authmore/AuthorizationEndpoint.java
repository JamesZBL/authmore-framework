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
import me.zbl.reactivesecurity.common.RandomPassword;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static me.zbl.authmore.OAuthException.INVALID_CLIENT;
import static me.zbl.authmore.OAuthException.INVALID_SCOPE;
import static me.zbl.authmore.OAuthException.UNSUPPORTED_RESPONSE_TYPE;
import static me.zbl.authmore.OAuthUtil.scopeSet;
import static me.zbl.authmore.SessionProperties.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-14
 */
@Controller
public class AuthorizationEndpoint {

    private AuthenticationManager authenticationManager;
    private CodeManager codeManager;

    public AuthorizationEndpoint(AuthenticationManager authenticationManager, CodeManager codeManager) {
        this.authenticationManager = authenticationManager;
        this.codeManager = codeManager;
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
        ClientDetails client = authenticationManager.clientValidate(clientId, redirectUri, scope);
        if (isEmpty(scope)) {
            throw new AuthorizationException(INVALID_SCOPE);
        }
        if (!"code".equals(responseType)) {
            throw new AuthorizationException(UNSUPPORTED_RESPONSE_TYPE);
        }
        if (client.isAutoApprove()) {
            String code = RandomPassword.create();
            String userId = user.getId();
            codeManager.saveCodeBinding(client, code, scopeSet(scope), redirectUri, userId);
            String location = String.format("%s?code=%s&state=%s", redirectUri, code, state);
            response.sendRedirect(location);
        }
        if (!isEmpty(state)) {
            session.setAttribute(LAST_STATE, state);
        }
        session.setAttribute(CURRENT_REDIRECT_URI, redirectUri);
        session.setAttribute(CURRENT_CLIENT, client);
        session.setAttribute(LAST_SCOPE, scope);
        model.addAttribute("client", client);
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
            HttpServletResponse response) throws IOException {
        if (null == client || !client.getClientId().equals(clientId))
            throw new AuthorizationException(INVALID_CLIENT);
        if (!"allow".equals(opinion))
            throw new AuthorizationException("signin was rejected");
        String code = RandomPassword.create();
        String userId = user.getId();
        codeManager.saveCodeBinding(client, code, scopeSet(scope), redirectUri, userId);
        String location = String.format("%s?code=%s&state=%s", redirectUri, code, state);
        response.sendRedirect(location);
    }
}
