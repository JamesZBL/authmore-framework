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

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
@ConfigurationProperties(prefix = "authmore.client")
public final class ClientProperties {

    private String clientId;

    private String clientSecret;

    private String tokenIssueUrl;

    private String authorizeUrl;

    private Boolean requestTokenOnStartup = false;

    private String scope;

    private String redirectUri;

    private String implicitTokenUri;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getTokenIssueUrl() {
        return tokenIssueUrl;
    }

    public void setTokenIssueUrl(String tokenIssueUrl) {
        this.tokenIssueUrl = tokenIssueUrl;
    }

    public boolean isRequestTokenOnStartup() {
        return requestTokenOnStartup;
    }

    public void setRequestTokenOnStartup(boolean requestTokenOnStartup) {
        this.requestTokenOnStartup = requestTokenOnStartup;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizeUri() {
        return authorizeUrl;
    }

    public void setAuthorizeUri(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getImplicitTokenUri() {
        return implicitTokenUri;
    }

    public void setImplicitTokenUri(String implicitTokenUri) {
        this.implicitTokenUri = implicitTokenUri;
    }
}
