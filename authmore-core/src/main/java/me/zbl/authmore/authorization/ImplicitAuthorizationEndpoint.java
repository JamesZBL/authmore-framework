/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zbl.authmore.authorization;

import me.zbl.authmore.client.ClientConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-17
 */
@Controller
public class ImplicitAuthorizationEndpoint {

    private final ClientConfigurationProperties clientProperties;

    @Autowired
    public ImplicitAuthorizationEndpoint(ClientConfigurationProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @GetMapping("/implicit.html")
    public String view(Model model) {
        String callBackUri = clientProperties.getImplicitTokenUri();
        model.addAttribute("callBackUri", callBackUri);
        return "implicit";
    }
}
