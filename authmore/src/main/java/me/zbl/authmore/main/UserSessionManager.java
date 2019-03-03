/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
