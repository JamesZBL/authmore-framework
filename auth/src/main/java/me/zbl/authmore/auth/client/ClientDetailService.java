package me.zbl.authmore.auth.client;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Component
public class ClientDetailService implements ClientDetailsService {

    private ClientDetailsRepo clientDetailsRepo;

    public ClientDetailService(ClientDetailsRepo clientDetailsRepo) {
        this.clientDetailsRepo = clientDetailsRepo;
    }

    @Override
    public ClientDetails loadClientByClientId(String id) throws ClientRegistrationException {
        return clientDetailsRepo.findByClientId(id).orElse(null);
    }
}
