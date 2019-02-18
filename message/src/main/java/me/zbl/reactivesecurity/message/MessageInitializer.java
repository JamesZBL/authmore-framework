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
package me.zbl.reactivesecurity.message;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author JamesZBL
 * @since 2019-01-23
 */
@Component
public class MessageInitializer implements SmartInitializingSingleton {

    private MessageRepository messages;

    public MessageInitializer(MessageRepository messages) {
        this.messages = messages;
    }

    private static final String tomId = "1";
    private static final String jamesId = "2";

    @Override
    public void afterSingletonsInstantiated() {
        messages.save(new Message(1L, tomId, jamesId, "Hi, James!")).block();
        messages.save(new Message(2L, tomId, jamesId, "How are you?")).block();
        messages.save(new Message(3L, tomId, jamesId, "Let's have dinner together.")).block();
        messages.save(new Message(4L, tomId, jamesId, "When will you go home?")).block();
        messages.save(new Message(5L, tomId, jamesId, "What are you doing now?")).block();
        messages.save(new Message(6L, jamesId, tomId, "Hi, Tom!")).block();
    }
}
