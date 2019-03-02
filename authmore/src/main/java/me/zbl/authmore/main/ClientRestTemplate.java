/*
 * Copyright 2019 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore.main;

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
 * @author JamesZBL
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
