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

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author JamesZBL
 * @email 1146556298@qq.com
 * @date 2019-01-23
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageRepository messages;

    public MessageController(MessageRepository messages) {
        this.messages = messages;
    }

    @GetMapping("/inbox")
    Flux<Message> inbox(@RequestParam("user-id") String userId) {
        return messages.findByTo(userId);
    }

    @GetMapping("/{id}")
    Mono<Message> findById(@PathVariable("id") Long id) {
        return messages.findById(id);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteById(@PathVariable("id") Long id) {
        return messages.deleteById(id);
    }
}
