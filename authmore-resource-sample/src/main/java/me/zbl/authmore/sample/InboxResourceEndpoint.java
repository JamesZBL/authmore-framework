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

package me.zbl.authmore.sample;

import me.zbl.authmore.resource.AuthorityRequired;
import me.zbl.authmore.resource.ScopeRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
@RestController
public class InboxResourceEndpoint {

    @GetMapping("/inbox")
    @ScopeRequired("EMAIL")
    @AuthorityRequired("READ")
    public Inbox inbox() {
        return new Inbox(Arrays.asList(
                new Email().setSubject("Hi, Tom! ").setFrom("James").setTo("Tom").setContent("Hello, Tom!"),
                new Email().setSubject("Hi, James! ").setFrom("Tom").setTo("James").setContent("Hi, James!"),
                new Email().setSubject("Go hiking! ").setFrom("Tony").setTo("James").setContent("James, Let's go hiking!")));
    }
}
