package com.khedmatkar.demo.messaging.controller;

import com.khedmatkar.demo.messaging.entity.Chat;
import com.khedmatkar.demo.messaging.entity.Message;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.messaging.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/chats")
public class ChatController {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ChatController(ChatRepository chatRepository,
                          MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/")
    public void sendMessage(@RequestBody Message message    ) {
        messageRepository.save(message);
    }

    @GetMapping("/{id}")
    public Chat getChat(@PathVariable(name = "id") Long id) {
        return chatRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chat not found")
        );
    }
}
