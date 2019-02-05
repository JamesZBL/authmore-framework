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
package me.zbl.reactsecurity.common.entity;

import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

/**
 * @author JamesZBL
 * created at 2019-01-28
 */
public class ResponseEntity extends org.springframework.http.ResponseEntity<ResponseContent> {

    public ResponseEntity(HttpStatus status) {
        super(status);
    }

    public ResponseEntity(ResponseContent body, HttpStatus status) {
        super(body, status);
    }

    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseEntity(ResponseContent body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
