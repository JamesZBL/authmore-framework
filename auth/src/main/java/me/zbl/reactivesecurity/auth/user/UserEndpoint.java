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
package me.zbl.reactivesecurity.auth.user;

import me.zbl.reactsecurity.common.entity.BasicController;
import me.zbl.reactsecurity.common.entity.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JamesZBL
 * created at 2019-01-28
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('SA')")
public class UserEndpoint extends BasicController {

    private UserDetailsRepo users;

    public UserEndpoint(UserDetailsRepo users) {
        this.users = users;
    }

    @GetMapping
    public List<UserDetails> all() {
        return users.findAll();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody UserDetails user) {
        users.save(user);
        return success();
    }

    @GetMapping("/{id}")
    public UserDetails byId(@PathVariable("id") String id) {
        return users.findById(id).orElse(null);
    }

    @PutMapping()
    public ResponseEntity delete(@RequestBody UserDetails user) {
        users.save(user);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        users.deleteById(id);
        return success();
    }
}
