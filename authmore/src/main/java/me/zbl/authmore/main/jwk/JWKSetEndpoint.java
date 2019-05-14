package me.zbl.authmore.main.jwk;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-14
 */
@RestController
public class JWKSetEndpoint {

    private final JWKSet jwkSetBean;

    public JWKSetEndpoint(JWKSet jwkSet) {
        this.jwkSetBean = jwkSet;
    }

    @GetMapping("/oauth/jwks")
    public Map<String, Object> jwkSet() {
        return this.jwkSetBean.toJSONObject();
    }
}
