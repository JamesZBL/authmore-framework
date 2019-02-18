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
import me.zbl.reactsecurity.common.RandomPassword;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author JamesZBL
 * @since 2019-02-11
 */
@RestController
@RequestMapping("/password")
public class PasswordEndpoint extends BasicController {

    @GetMapping("/random")
    public Map randomPassword() {
        return map().put("result", RandomPassword.create()).map();
    }
}

