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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public final class RequestUtil {

    private RequestUtil() {}

    public static String queryStringOf(Map<String, String> params) {
        List<String> stringParis = new ArrayList<>();
        params.forEach((k, v) -> stringParis.add(String.format("%s=%s", String.valueOf(k), String.valueOf(v))));
        return String.join("&", stringParis);
    }
}
