package me.zbl.authmore.main;

import me.zbl.authmore.core.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-14
 */
@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {

    Optional<UserDetails> findByUsername(String userName);
}
