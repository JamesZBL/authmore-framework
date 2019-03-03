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
package me.zbl.reactivesecurity.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-11
 */
public class ResultBuilder {

    private Map<String, Object> map;

    public ResultBuilder() {
        this.map = new HashMap<>();
    }

    public ResultBuilder put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map map() {
        return this.map;
    }
}
