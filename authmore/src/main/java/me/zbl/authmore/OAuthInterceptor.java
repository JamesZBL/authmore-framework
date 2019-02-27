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

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static me.zbl.authmore.OAuthProperties.REQUEST_SCOPES;

/**
 * @author JamesZBL
 * @since 2019-02-27
 */
@Component
public class OAuthInterceptor implements HandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HandlerMethod method = (HandlerMethod) handler;
        ScopeRequired scope = method.getMethodAnnotation(ScopeRequired.class);
        if (null != scope) {
            Set<String> scopes = (Set<String>) request.getAttribute(REQUEST_SCOPES);
            String[] scopesNeeded = scope.value();
            OAuthProperties.RequireTypes type = scope.type();
            boolean support = OAuthUtil.support(type, scopesNeeded, scopes);
            if (!support) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }
}
