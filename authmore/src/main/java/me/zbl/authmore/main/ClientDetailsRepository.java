package me.zbl.authmore.main;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
@Repository
public interface ClientDetailsRepository extends MongoRepository<ClientDetails, String> {

    Optional<ClientDetails> findByClientId(String clientId);
}
