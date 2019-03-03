package me.zbl.authmore.auth.client;

import me.zbl.authmore.auth.AuthController;
import me.zbl.authmore.auth.DataWrapper;
import me.zbl.authmore.core.ClientDetails;
import me.zbl.reactivesecurity.common.RandomSecret;
import me.zbl.reactivesecurity.common.entity.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
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
        String secret = RandomSecret.create();
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
