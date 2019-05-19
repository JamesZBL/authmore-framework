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

package me.zbl.authmore.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-24
 */
class UniqueTokenTest {

    @Test
    void create() {
        Set<Object> set = new HashSet<>();
        for (int i = 0; i < 10E6; i++) {
            String token = UniqueToken.create();
            assertNotNull(token);
            assertFalse(set.contains(token));
            set.add(token);
        }
    }
}