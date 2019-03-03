package me.zbl.authmore.main;

import me.zbl.authmore.core.UserDetails;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
public interface SessionManager {

    void signin(UserDetails user);
}
