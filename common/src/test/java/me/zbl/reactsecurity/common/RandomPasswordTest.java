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
package me.zbl.reactsecurity.common;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JamesZBL
 * created at 2019-02-10
 */
public class RandomPasswordTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomPasswordTest.class);

    @BeforeEach
    void setup() {
        BasicConfigurator.configure();
    }

    @Test
    void build() {
        String last = null;
        HashSet<Object> digests = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String pwd = RandomPassword.build();
            char[] chars = pwd.toCharArray();
            assertNotNull(pwd);
            assertEquals(32, pwd.length());
            assertNotEquals(last, pwd);
            for (int j = 0; j < pwd.length(); j++) {
                char c = chars[j];
                assertTrue(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z');
            }
            assertFalse(digests.contains(pwd));
            digests.add(pwd);
            last = pwd;
            LOGGER.debug("New random password: {}", pwd);
        }
    }
}
