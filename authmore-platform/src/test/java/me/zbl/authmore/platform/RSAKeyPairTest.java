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

package me.zbl.authmore.platform;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-14
 */
public class RSAKeyPairTest {

    @Test
    public void testJwkPairGeneration() throws JOSEException {

        String keyId = UUID.randomUUID().toString();
        // Generate 2048-bit RSA key pair in JWK format, attach some metadata
        RSAKey jwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                .keyID(keyId) // give the key a unique ID
                .generate();

        assertEquals(jwk.getKeyType(), KeyType.RSA);
        assertEquals(jwk.getKeyID(), keyId);
        assertEquals(jwk.getKeyUse(), KeyUse.SIGNATURE);
    }
}
