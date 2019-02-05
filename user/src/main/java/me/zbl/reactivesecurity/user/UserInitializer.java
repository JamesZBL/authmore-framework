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

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author JamesZBL
 * created at 2019-01-23
 */
@Component
public class UserInitializer implements SmartInitializingSingleton {

    private UserRepo users;

    /** 123456 **/
    private static final String password = "200c5b9f4f43daff4f76d84f9c6c5d5441a94faf8768bcbf19c4333de2aa24ad0d38d54aaeadf634";

    public UserInitializer(UserRepo users) {
        this.users = users;
    }

    @Override
    public void afterSingletonsInstantiated() {
        users.save(new User(1L, "tom@example.com", password, "Tom", "Lee")).block();
        users.save(new User(2L, "james@example.com", password, "James", "Zheng")).block();

        users.findAll()
                .doOnNext(u -> u.setPassword("{sha256}" + u.getPassword()))
                .flatMap(users::save)
                .collectList()
                .block();
    }
}
