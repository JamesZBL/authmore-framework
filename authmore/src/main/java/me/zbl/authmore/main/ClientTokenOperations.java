package me.zbl.authmore.main;

import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public interface ClientTokenOperations {

    TokenResponse getToken(String scope, Map<String, String> restParams);
}
