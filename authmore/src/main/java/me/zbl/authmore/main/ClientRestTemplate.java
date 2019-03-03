package me.zbl.authmore.main;

import me.zbl.reactivesecurity.common.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public class ClientRestTemplate extends RestTemplate {

    public ClientRestTemplate(String token) {
        Assert.notEmpty(token, "empty token");
        addTokenInterceptor(token);
    }

    private void addTokenInterceptor(String token) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        setInterceptors(Stream.of(tokenInterceptor).collect(Collectors.toList()));
    }

    private class TokenInterceptor implements ClientHttpRequestInterceptor {

        private final String token;

        private TokenInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return execution.execute(request, body);
        }
    }
}
