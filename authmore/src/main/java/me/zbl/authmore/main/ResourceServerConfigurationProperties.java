package me.zbl.authmore.main;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
@ConfigurationProperties(prefix = "authmore.resource")
public class ResourceServerConfigurationProperties {

    private String resourceId;

    private String tokenInfoUrl;

    private String clientId;

    private String clientSecret;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTokenInfoUrl() {
        return tokenInfoUrl;
    }

    public void setTokenInfoUrl(String tokenInfoUrl) {
        this.tokenInfoUrl = tokenInfoUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
