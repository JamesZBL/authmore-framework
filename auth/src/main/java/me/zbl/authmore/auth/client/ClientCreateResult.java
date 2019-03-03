package me.zbl.authmore.auth.client;

import me.zbl.authmore.core.ClientDetails;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-09
 */
public class ClientCreateResult {

    private String clientId;
    private String clientSecret;

    private ClientCreateResult() { }

    public static ClientCreateResult build(ClientDetails clientDetails, String originalSecret) {
        return new ClientCreateResult(clientDetails.getClientId(), originalSecret);
    }

    private ClientCreateResult(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
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
