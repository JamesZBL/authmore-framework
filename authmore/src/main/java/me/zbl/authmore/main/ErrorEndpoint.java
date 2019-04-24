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
package me.zbl.authmore.main;

import io.swagger.annotations.Api;
import me.zbl.authmore.main.oauth.OAuthErrorResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-05
 */
@Api(description = "异常响应")
@RestController
public class ErrorEndpoint extends AbstractErrorController {

    public ErrorEndpoint(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping("/error")
    public ErrorResponse error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, false);
        String message = (String) errorAttributes.getOrDefault("message", "unknown error");
        return new OAuthErrorResponse(message, "no description");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
