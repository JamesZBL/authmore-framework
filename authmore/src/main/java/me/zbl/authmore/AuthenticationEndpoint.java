/*
 * Copyright 2019 JamesZBL
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
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.user.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @author JamesZBL
 * created at 2019-02-15
 */
@Controller
public class AuthenticationEndpoint {

    private static final String ERROR = "error";

    private UserDetailsRepo users;
    private PasswordEncoder passwordEncoder;

    public AuthenticationEndpoint(UserDetailsRepo users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    @ResponseBody
    public List<UserDetails> users() {
        return users.findAll();
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String signInPage() {
        return "signin";
    }

    @PostMapping("/signin")
    public String singIn(@RequestParam("ui") String userName, @RequestParam("uc") String inputPassword,
                         @RequestParam("from") String from, Model model, HttpSession session) {
        Optional<UserDetails> find = users.findByUsername(userName);
        if (!find.isPresent()) {
            model.addAttribute(ERROR, "Invalid username!");
        } else {
            UserDetails user = find.get();
            String storedPassword = user.getPassword();
            boolean valid = passwordEncoder.matches(inputPassword, storedPassword);
            boolean enabled = user.isEnabled();
            if (valid) {
                if(!enabled)
                    model.addAttribute(ERROR,"Account is disabled!");
                else {
                    session.setAttribute(SessionProperties.SESSION_DETAILS, new SessionDetails(user));
                    if (!StringUtils.isEmpty(from))
                        return "redirect:" + from;
                    else return "/";
                }
            } else model.addAttribute(ERROR, "Invalid password!");
        }
        return "/signin";
    }
}
