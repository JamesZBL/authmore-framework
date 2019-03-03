package me.zbl.authmore.auth.user;

import me.zbl.authmore.core.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Repository
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {

    Optional<UserDetails> findByUsername(String username);

    List<UserDetails> findAllByOrderByIdDesc();

    void deleteByIdIn(List<String> id);
}
