package me.zbl.authmore.auth;

import me.zbl.authmore.core.PasswordHolder;
import me.zbl.reactivesecurity.common.BasicController;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-05
 */
public class AuthController extends BasicController {

    private PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected void encodePassword(PasswordHolder holder) {
        String raw = holder.getPassword();
        String encoded = passwordEncoder.encode(raw);
        holder.setPassword(encoded);
    }
}
