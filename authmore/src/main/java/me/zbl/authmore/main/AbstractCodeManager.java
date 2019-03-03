package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public abstract class AbstractCodeManager implements CodeManager {

    @Override
    public void saveCodeBinding(ClientDetails client, String code, Set<String> scopes, String redirectUri, String userId) {
        String clientId = client.getClientId();
        CodeBinding codeBinding = new CodeBinding(code, clientId, scopes, redirectUri, userId);
        saveCode(codeBinding);
    }

    abstract void saveCode(CodeBinding codeBinding);
}
