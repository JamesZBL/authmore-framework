package me.zbl.authmore.main;

import static me.zbl.authmore.main.OAuthException.INVALID_CODE;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public class RedisCodeManager extends AbstractCodeManager {

    private final CodeRepository authorizationCodes;

    public RedisCodeManager(CodeRepository authorizationCodes) {
        super();
        this.authorizationCodes = authorizationCodes;
    }

    @Override
    void saveCode(CodeBinding codeBinding) {
        authorizationCodes.save(codeBinding);
    }

    @Override
    public CodeBinding getCodeDetails(String clientId, String code) {
        return authorizationCodes.findById(code)
                .orElseThrow(() -> new OAuthException(INVALID_CODE));
    }

    @Override
    public void expireCode(String code) {
        authorizationCodes.deleteById(code);
    }
}
