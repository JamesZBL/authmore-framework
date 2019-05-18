/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zbl.authmore;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import me.zbl.authmore.core.ClientDetails;
import me.zbl.authmore.main.Bootstrap;
import me.zbl.authmore.main.client.ClientDetailsRepository;
import me.zbl.authmore.main.oauth.JSONWebTokenManager;
import me.zbl.authmore.main.oauth.OAuthProperties;
import me.zbl.authmore.main.oauth.TokenResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.text.ParseException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Bootstrap.class})
public class BootstrapTests {

    @Autowired
    private KeyPair keyPair;
    @Autowired
    private ClientDetailsRepository clients;
    @Autowired
    private JWKSet jwkSet;

    @Test
    public void testJSONWebTokenManager() throws ParseException, JOSEException, BadJOSEException {

        JSONWebTokenManager tokens = new JSONWebTokenManager(clients, keyPair);
        ClientDetails client = clients.findAll().get(0);
        String userId = "user_1";
        TokenResponse tokenResponse = tokens.create(client, userId, Collections.emptySet());
        String accessToken;
        assertNotNull(tokenResponse);
        assertNotNull(accessToken = tokenResponse.getAccess_token());
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWKSource<SecurityContext> keySource = new ImmutableJWKSet<>(jwkSet);
        JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);
        JWTClaimsSet claimsSet = jwtProcessor.process(accessToken, null);
        assertEquals(userId, claimsSet.getClaim(OAuthProperties.TOKEN_USER_ID));
    }
}

