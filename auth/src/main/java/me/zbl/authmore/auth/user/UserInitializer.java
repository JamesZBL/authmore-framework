package me.zbl.authmore.auth.user;

import me.zbl.authmore.core.UserDetails;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Component
public class UserInitializer implements SmartInitializingSingleton {

    private UserDetailsRepo users;
    private PasswordEncoder passwordEncoder;

    public UserInitializer(UserDetailsRepo users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterSingletonsInstantiated() {
        UserDetails user = new UserDetails("james", passwordEncoder.encode("123456"), "SA");
        try {
            users.save(user);
        } catch (DuplicateKeyException ignored) {
        }
    }
}
