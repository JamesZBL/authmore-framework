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
package me.zbl.reactivesecurity.auth.client;

import me.zbl.reactsecurity.common.entity.BasicController;
import me.zbl.reactsecurity.common.entity.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JamesZBL
 * created at 2019-01-28
 */
@RestController
@RequestMapping("/client")
@PreAuthorize("hasAuthority('SA')")
public class ClientEndpoint extends BasicController {

    private ClientDetailsRepo clients;
    private PasswordEncoder passwordEncoder;

    public ClientEndpoint(ClientDetailsRepo clients, PasswordEncoder passwordEncoder) {
        this.clients = clients;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<ClientDetails> clientDetails() {
        return clients.findAll();
    }

    @GetMapping("/{id}")
    public ClientDetails clientDetails(@PathVariable("id") String id) {
        return clients.findByClientId(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody ClientDetails client) {
//        client.setClientSecret();
        clients.save(client);
        return success();
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody ClientDetails user) {
        clients.save(user);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        clients.deleteById(id);
        return success();
    }
}
