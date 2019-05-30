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

package me.zbl.authmore.platform.authorization;

import java.util.Arrays;

/**
 * @author ZHENG BAO LE
 * @since 2019-05-30
 */
public enum ScopeConstants {

    AVATAR("头像"),
    EMAIL("邮件"),
    PROFILE("个人资料"),
    NICKNAME("昵称"),
    GENDER("性别"),
    AGE("年龄"),
    ADDRESS("地址"),
    UNKNOWN("其它范围");

    private String name;

    ScopeConstants(String name) {
        this.name = name;
    }

    public String findByKey() {
        return name;
    }

    public static String findByKey(String key) {
        return Arrays.stream(ScopeConstants.values()).filter(v -> v.name().equals(key))
                .findFirst()
                .orElse(ScopeConstants.UNKNOWN)
                .findByKey();
    }
}
