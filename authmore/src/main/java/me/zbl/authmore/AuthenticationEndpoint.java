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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * created at 2019-02-15
 */
@Controller
public class AuthenticationEndpoint {

    private static final String ERROR = "error";

    private AuthenticationManager authenticationManager;
    private SessionManager sessionManager;

    public AuthenticationEndpoint(AuthenticationManager authenticationManager, SessionManager sessionManager) {
        this.authenticationManager = authenticationManager;
        this.sessionManager = sessionManager;
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
                         @RequestParam("from") String from, Model model) {
        UserDetails user;
        try {
            user = authenticationManager.userValidate(userName, inputPassword);
        } catch (AuthenticationException e) {
            model.addAttribute(ERROR, e.getMessage());
            return "/signin";
        }
        sessionManager.signin(user);
        if (!isEmpty(from))
            return "redirect:" + from;
        return "redirect:/";
    }
}
