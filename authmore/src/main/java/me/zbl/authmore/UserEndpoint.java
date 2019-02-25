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
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.user.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JamesZBL
 * @since 2019-02-25
 */
@RestController
public class UserEndpoint {

    private UserDetailsRepository users;

    public UserEndpoint(UserDetailsRepository users) {
        this.users = users;
    }

    @GetMapping("/user/details")
    public UserDetails userDetails(
            @RequestParam("user_id") String userId) {
        return users.findById(userId).orElseThrow(() -> new OAuthException("no such user"));
    }
}
