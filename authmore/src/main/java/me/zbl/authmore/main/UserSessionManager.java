package me.zbl.authmore.main;

import me.zbl.authmore.core.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static me.zbl.authmore.main.SessionProperties.CURRENT_USER;
import static me.zbl.authmore.main.SessionProperties.CURRENT_USER_DETAILS;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-15
 */
@Service
public class UserSessionManager implements SessionManager {

    private HttpSession session;

    public UserSessionManager(HttpSession session) {
        this.session = session;
    }

    @Override
    public void signin(UserDetails user) {
        session.setAttribute(CURRENT_USER_DETAILS, user);
        session.setAttribute(CURRENT_USER, user.getUsername());
    }
}
