package com.khedmatkar.demo.messaging.controller;

import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.messaging.dto.ChatDTO;
import com.khedmatkar.demo.messaging.dto.MessageDTO;
import com.khedmatkar.demo.messaging.entity.Chat;
import com.khedmatkar.demo.messaging.entity.ChatStatus;
import com.khedmatkar.demo.messaging.entity.Message;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.messaging.repository.MessageRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/chats")
public class ChatController {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final AccountService accountService;

    public ChatController(ChatRepository chatRepository,
                          MessageRepository messageRepository,
                          ServiceRequestRepository serviceRequestRepository,
                          AccountService accountService) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.accountService = accountService;
    }

    @RolesAllowed(UserType.Role.USER)
    @PostMapping("/")
    public void sendMessage(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestBody MessageDTO dto) {
        var sender = accountService.findConcreteUserClassFromUserDetails(userDetails);
        var chat = chatRepository.findById(dto.chatId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chat not found")
        );
        if (chat.getStatus() == ChatStatus.CLOSED){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "chat is closed");
        }
        messageRepository.save(Message.builder()
                .sender(sender)
                .text(dto.text)
                .chat(chat)
                .build());
    }

    @RolesAllowed(UserType.Role.USER)
    @GetMapping("/{id}")
    @Transactional
    public ChatDTO getChat(@PathVariable(name = "id") Long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chat not found")
        );
        return ChatDTO.builder()
                .chatId(chat.getId())
                .status(String.valueOf(chat.getStatus()))
                .userAEmail(chat.getUserA().getEmail())
                .userBEmail(chat.getUserB().getEmail())
                .messages(chat.getMessages().stream()
                        .map(message -> MessageDTO.builder()
                                .senderEmail(message.getSender().getEmail())
                                .text(message.getText())
                                .timeStamp(message.getCreation())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @RolesAllowed(UserType.Role.USER)
    @GetMapping("/serviceRequest/{id}")
    @Transactional
    public List<ChatDTO> getChats(@PathVariable(name = "id") Long serviceRequestId) {
        var serviceRequest = serviceRequestRepository.findById(serviceRequestId).orElseThrow(
                ServiceRequestNotFoundException::new
        );
        return serviceRequest.getChats().stream()
                .map(chat -> ChatDTO.builder()
                        .chatId(chat.getId())
                        .status(String.valueOf(chat.getStatus()))
                        .userAEmail(chat.getUserA().getEmail())
                        .userBEmail(chat.getUserB().getEmail())
                        .messages(chat.getMessages().stream()
                                .map(message -> MessageDTO.builder()
                                        .senderEmail(message.getSender().getEmail())
                                        .text(message.getText())
                                        .timeStamp(message.getCreation())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }
}
