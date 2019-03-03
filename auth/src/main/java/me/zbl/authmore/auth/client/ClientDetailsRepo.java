package me.zbl.authmore.auth.client;

import me.zbl.authmore.core.ClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Repository
public interface ClientDetailsRepo extends MongoRepository<ClientDetails, String> {

    Optional<ClientDetails> findByClientId(String clientId);

    Collection<ClientDetails> findByClientName(String clientName);

    List<ClientDetails> findAllByOrderByClientIdDesc();

    void deleteByClientIdIn(List<String> id);
}
