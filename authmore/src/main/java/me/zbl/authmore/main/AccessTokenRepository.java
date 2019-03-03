package me.zbl.authmore.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-21
 */
@Repository
public interface AccessTokenRepository extends CrudRepository<AccessTokenBinding, String> {}
