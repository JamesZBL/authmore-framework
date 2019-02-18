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
package me.zbl.reactivesecurity.user;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author JamesZBL
 * @since 2019-01-23
 */
@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    private UserRepo users;
    private Random random = new SecureRandom();

    public UserController(UserRepo users) {
        this.users = users;
    }

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping
    public Flux<User> users() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable("id") Long id) {
        return users.findById(id);
    }

    @GetMapping(params = "email")
    public Mono<User> findByEmail(@RequestParam String email) {
        return users.findByEmail(email);
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        return Mono.justOrEmpty(user)
                .doOnSuccess(u -> {
                    if (null == u.getId()) {
                        u.setId(random.nextLong());
                    }
                })
                .publishOn(Schedulers.parallel())
                .flatMap(users::save);
    }
}
