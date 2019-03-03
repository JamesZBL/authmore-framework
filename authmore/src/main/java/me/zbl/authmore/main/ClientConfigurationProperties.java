package me.zbl.authmore.main;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
@ConfigurationProperties(prefix = "authmore.client")
public final class ClientConfigurationProperties {

    private String clientId;

    private String clientSecret;

    private String tokenIssueUrl;

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
}
