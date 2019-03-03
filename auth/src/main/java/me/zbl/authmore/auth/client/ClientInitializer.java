package me.zbl.authmore.auth.client;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Component
public class ClientInitializer implements SmartInitializingSingleton {

    private ClientDetailsRepo clientDetailsRepo;

    public ClientInitializer(ClientDetailsRepo clientDetailsRepo) {
        this.clientDetailsRepo = clientDetailsRepo;
    }

    @Override
    public void afterSingletonsInstantiated() {
        ClientDetails client = new ClientDetails("cartapp",
                "authorization_code,password,implicit,client_credentials,refresh_token", "PROFILE",
                "{pbkdf2}30d47c8ef17066e65750bb6469b951dbaf8b40d4cf4b421490ffff92da00804700c8b8fb92cc9ce0",
                "http://localhost:8084/login", 999999);

        clientDetailsRepo.save(client);
    }
}
