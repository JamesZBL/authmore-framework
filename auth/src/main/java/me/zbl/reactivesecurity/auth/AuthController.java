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
package me.zbl.reactivesecurity.auth;

import me.zbl.reactsecurity.common.BasicController;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author JamesZBL
 * created at 2019-02-05
 */
public class AuthController extends BasicController {

    private PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected void encodePassword(PasswordHolder holder) {
        String raw = holder.getPassword();
        String encoded = passwordEncoder.encode(raw);
        holder.setPassword(encoded);
    }
}
