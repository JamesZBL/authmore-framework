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

import me.zbl.reactivesecurity.auth.AuthController;
import me.zbl.reactivesecurity.auth.DataWrapper;
import me.zbl.reactsecurity.common.RandomPassword;
import me.zbl.reactsecurity.common.entity.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author JamesZBL
 * created at 2019-01-28
 */
@RestController
@RequestMapping("/client")
@PreAuthorize("hasAuthority('SA')")
public class ClientEndpoint extends AuthController {

    private ClientDetailsRepo clients;

    public ClientEndpoint(ClientDetailsRepo clients, PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
        this.clients = clients;
    }

    @GetMapping
    public DataWrapper<ClientDetails> clientDetails() {
        List<ClientDetails> all = clients.findAllByOrderByClientIdDesc();
        return new DataWrapper<>(all);
    }

    @GetMapping("/{id}")
    public ClientDetails clientDetails(@PathVariable("id") String id) {
        return clients.findByClientId(id).orElse(null);
    }

    @PostMapping
    public ClientCreateResult add(@RequestBody ClientDetails client) {
        String secret = RandomPassword.create();
        client.setClientSecret(secret);
        encodePassword(client);
        clients.save(client);
        return ClientCreateResult.build(client, secret);
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody ClientDetails client) {
        String clientId = client.getClientId();
        Optional<ClientDetails> original = clients.findById(clientId);
        original.ifPresent(o -> client.setClientSecret(o.getClientSecret()));
        clients.save(client);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        clients.deleteById(id);
        return success();
    }

    @GetMapping("/exist")
    public Map exist(@RequestParam("clientName") String name) {
        Collection<ClientDetails> find = clients.findByClientName(name);
        boolean exist = !find.isEmpty();
        return exist(exist);
    }

    @DeleteMapping()
    public ResponseEntity deleteBatch(@RequestBody List<String> ids) {
        clients.deleteByClientIdIn(ids);
        return success();
    }
}
