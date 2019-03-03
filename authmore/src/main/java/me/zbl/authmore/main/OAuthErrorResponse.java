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

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-19
 */
public final class OAuthErrorResponse {

    private final String error;
    private final String error_description;

    public OAuthErrorResponse(OAuthException e) {
        this(e.getMessage(), e.getErrorDescription());
    }

    public OAuthErrorResponse(MissingServletRequestParameterException e) {
        this("invalid request parameters", e.getMessage());
    }

    public OAuthErrorResponse(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }
}
