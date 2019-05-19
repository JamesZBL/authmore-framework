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
package me.zbl.authmore.client;

import me.zbl.authmore.common.Assert;
import me.zbl.authmore.oauth.OAuthProperties;
import me.zbl.authmore.oauth.OAuthProperties.ResponseTypes;
import me.zbl.authmore.oauth.RequestUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-07
 */
public class AuthorizationTemplate implements AuthorizationOperations {

    private final String authorizeUrl;
    private final String clientId;
    private final String redirectUrl;
    private final String implicitRedirectUrl;

    public AuthorizationTemplate(ClientConfigurationProperties client) {
        this.authorizeUrl = client.getAuthorizeUri();
        this.clientId = client.getClientId();
        this.redirectUrl = client.getRedirectUri();
        this.implicitRedirectUrl = client.getImplicitRedirectUri();
    }

    @Override
    public void redirectToUserAuthorize(HttpServletResponse response, ResponseTypes type, String scope)
            throws IOException {
        String redirect;
        if (type == ResponseTypes.TOKEN) {
            redirect = implicitRedirectUrl;
        } else {
            redirect = redirectUrl;
        }
        Assert.notEmpty(authorizeUrl, "authorize url is required");
        Assert.notEmpty(redirect, "redirect url is required");
        Map<String, String> params = new HashMap<>();
        params.put(OAuthProperties.PARAM_RESPONSE_TYPE, type.getName());
        params.put(OAuthProperties.PARAM_CLIENT_ID, clientId);
        params.put(OAuthProperties.PARAM_REDIRECT_URI, redirect);
        params.put(OAuthProperties.PARAM_SCOPE, scope);
        response.sendRedirect(authorizeUrl + "?" + RequestUtil.queryStringOf(params));
    }
}
