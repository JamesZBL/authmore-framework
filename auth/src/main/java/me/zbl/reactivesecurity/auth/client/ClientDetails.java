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
package me.zbl.reactivesecurity.auth.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.zbl.reactivesecurity.auth.PasswordHolder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JamesZBL
 * created at 2019-01-28
 */
@Document
public class ClientDetails implements org.springframework.security.oauth2.provider.ClientDetails, PasswordHolder {

    @Id
    private String clientId;
    private String authorizedGrantTypes;
    private Boolean scoped;
    private String scope;
    private String resourceIds;
    private Boolean isSecretRequired = true;
    private String clientSecret;
    private String authorities;
    private String registeredRedirectUri;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Boolean isAutoApprove;
    private String additionalInformation;
    public ClientDetails() {}

    public ClientDetails(String clientId, String grantTypes, String scopes, String clientSecret,
                         Integer accessTokenValiditySeconds) {
        this.clientId = clientId;
        this.authorizedGrantTypes = grantTypes;
        this.scope = scopes;
        this.clientSecret = clientSecret;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public ClientDetails(String clientId, String grantTypes, String scopes, String clientSecret,
                         String registeredRedirectUri, Integer accessTokenValiditySeconds) {
        this.clientId = clientId;
        this.authorizedGrantTypes = grantTypes;
        this.scope = scopes;
        this.clientSecret = clientSecret;
        this.registeredRedirectUri = registeredRedirectUri;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return string2Set(resourceIds);
    }

    @Override
    public boolean isSecretRequired() {
        return isSecretRequired;
    }

    @JsonIgnore
    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return string2Set(scope);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return string2Set(authorizedGrantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return string2Set(registeredRedirectUri);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return string2Set(authorities)
                .stream().map(a -> (GrantedAuthority) () -> a)
                .collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return getClientSecret();
    }

    @Override
    public void setPassword(String encoded) {
        setClientSecret(encoded);
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private Set<String> string2Set(String raw) {
        if (StringUtils.isEmpty(raw)) {
            return Collections.emptySet();
        }
        return new HashSet<>(Arrays.asList(raw.split(",")));
    }
}
