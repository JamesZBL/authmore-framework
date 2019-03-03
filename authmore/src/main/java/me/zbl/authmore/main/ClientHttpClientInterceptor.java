package me.zbl.authmore.main;

import org.springframework.http.client.support.BasicAuthenticationInterceptor;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public class ClientHttpClientInterceptor extends BasicAuthenticationInterceptor {

    public ClientHttpClientInterceptor(String username, String password) {
        super(username, password);
    }
}
