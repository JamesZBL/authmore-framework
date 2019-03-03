package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public interface CodeManager {

    void saveCodeBinding(ClientDetails client, String code, Set<String> scopes, String redirectUri, String userId);

    CodeBinding getCodeDetails(String clientId, String code);

    void expireCode(String code);
}
