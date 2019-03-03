package me.zbl.authmore.main;

import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_ID;
import static me.zbl.authmore.main.OAuthProperties.PARAM_CLIENT_SECRET;
import static me.zbl.authmore.main.RequestUtil.queryStringOf;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
public class ResourceServerFilter extends OAuthFilter {

    private final ResourceServerConfigurationProperties resourceServerConfigurationProperties;

    public ResourceServerFilter(ResourceServerConfigurationProperties resourceServerConfigurationProperties) {
        this.resourceServerConfigurationProperties = resourceServerConfigurationProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token;
        String tokenInfoUrl;
        String clientId;
        String clientSecret;
        TokenCheckResponse tokenInfo;
        try {
            token = OAuthUtil.extractToken(request);
        } catch (Exception e) {
            reject(response);
            return;
        }
        tokenInfoUrl = resourceServerConfigurationProperties.getTokenInfoUrl();
        clientId = resourceServerConfigurationProperties.getClientId();
        clientSecret = resourceServerConfigurationProperties.getClientSecret();

        RestTemplate rest = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put(PARAM_CLIENT_ID, clientId);
        params.put(PARAM_CLIENT_SECRET, clientSecret);
        tokenInfo = rest.getForObject(
                tokenInfoUrl + "?" + queryStringOf(params), TokenCheckResponse.class);
        if (null == tokenInfo) {
            reject(response);
            return;
        }
        Set<String> tokenScopes = tokenInfo.getScope();
        Set<String> authorities = tokenInfo.getAuthorities();
        request.setAttribute(OAuthProperties.REQUEST_SCOPES, tokenScopes);
        request.setAttribute(OAuthProperties.REQUEST_AUTHORITIES, authorities);
        filterChain.doFilter(request, response);
    }
}
