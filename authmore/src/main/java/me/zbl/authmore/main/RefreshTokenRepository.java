package me.zbl.authmore.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-26
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenBinding, String> {
}
