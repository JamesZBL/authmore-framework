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

/**
 * @author JamesZBL
 * created at 2019-02-15
 */
interface SessionProperties {

    String SESSION_DETAILS = "session_details";
    String CURRENT_USER = "current_user";
    String CURRENT_USER_DETAILS = "current_user_details";
    String LAST_URL = "last_url";
    String CURRENT_CLIENT = "current_client";
    String CURRENT_REDIRECT_URI = "current_redirect_uri";
    String LAST_STATE = "last_state";
}
