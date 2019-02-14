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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author JamesZBL
 * created at 2019-02-14
 */
@Controller
public class TestEndpoint {

    private UserDetailsRepo users;

    public TestEndpoint(UserDetailsRepo users) {
        this.users = users;
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

    @GetMapping("/set_name")
    public String setName(HttpSession session) {
        session.setAttribute("application_name", "Authmore");
        return "redirect:index";
    }
}
