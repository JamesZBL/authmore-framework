package me.zbl.authmore.main;

import me.zbl.authmore.core.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static me.zbl.authmore.main.OAuthProperties.SCOPE_USER_DETAILS;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-25
 */
@RestController
public class UserDetailsEndpoint {

    private final UserDetailsRepository users;

    public UserDetailsEndpoint(UserDetailsRepository users) {
        this.users = users;
    }

    @GetMapping("/user/details")
    @ScopeRequired({SCOPE_USER_DETAILS})
    public UserDetails userDetails(
            @RequestParam("user_id") String userId) {
        return users.findById(userId).orElseThrow(() -> new OAuthException("no such user"));
    }
}
