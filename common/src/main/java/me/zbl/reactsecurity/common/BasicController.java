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
package me.zbl.reactsecurity.common;

import me.zbl.reactsecurity.common.entity.ResponseContent;
import me.zbl.reactsecurity.common.entity.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author JamesZBL
 * @since 2019-01-28
 */
public class BasicController {

    public ResponseEntity success() {
        return new ResponseEntity(new ResponseContent("", "success"), HttpStatus.OK);
    }

    public ResponseEntity error() {
        return new ResponseEntity(new ResponseContent("", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity badRequest() {
        return new ResponseEntity(new ResponseContent("", "invalid request"), HttpStatus.BAD_REQUEST);
    }

    public Map exist(boolean exist) {
        return map().put("result", exist).map();
    }

    public ResultBuilder map() {
        return new ResultBuilder();
    }
}
