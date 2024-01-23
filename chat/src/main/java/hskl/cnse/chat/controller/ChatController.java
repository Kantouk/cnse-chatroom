package hskl.cnse.chat.controller;

import org.springframework.web.bind.annotation.RestController;

import hskl.cnse.chat.db.dto.ChatCreationDto;
import hskl.cnse.chat.db.dto.ChatDTO;
import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.services.ChatService;
import hskl.cnse.chat.services.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatCreationDto chatCreationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Chat createdChat = chatService.createChat(chatCreationDto);
        ChatDTO rChatDto = new ChatDTO(createdChat);
        userService.updateChatsForUser(chatCreationDto.getUserId());
        return ResponseEntity.ok(rChatDto);
    }

    @PostMapping("/rename/{chatId}")
    public ResponseEntity<ChatDTO> renameChat(@RequestBody ChatCreationDto chatCreationDto, @PathVariable @NonNull Long chatId, @RequestBody Map<String, String> body, BindingResult result) {
        String password = body.get("password");
        if (result.hasErrors() || !chatService.checkPassword(chatId, password)) {
            return ResponseEntity.badRequest().build();
        }
        Chat renamedChat = chatService.renameChat(chatCreationDto, chatId);
        ChatDTO rChatDto = new ChatDTO(renamedChat);
        return ResponseEntity.ok(rChatDto);
    }

    @PostMapping("/delete/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable @NonNull Long chatId, @RequestBody Map<String, String> body, BindingResult result) {
        String password = body.get("password");
        if (result.hasErrors() || !chatService.checkPassword(chatId, password)) {
            return ResponseEntity.badRequest().build();
        }
        chatService.deleteChat(chatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> getChatById(@PathVariable @NonNull Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        ChatDTO rChatDto = new ChatDTO(chat);
        return ResponseEntity.ok(rChatDto);
    }

    @PostMapping("/addParticipant/{chatId}/{userId}")
    public ResponseEntity<Void> addParticipant(@PathVariable @NonNull Long chatId, @PathVariable @NonNull Long userId, @RequestBody Map<String, String> body, BindingResult result) {
        String password = body.get("password");
        if (result.hasErrors() || !chatService.checkPassword(chatId, password)) {
            return ResponseEntity.badRequest().build();
        }
        chatService.addParticipant(chatId, userId);
        userService.updateChatsForUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeParticipant/{chatId}/{userId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable @NonNull Long chatId, @PathVariable @NonNull Long userId, @RequestBody Map<String, String> body, BindingResult result) {
        String password = body.get("password");
        if (result.hasErrors() || !chatService.checkPassword(chatId, password)) {
            return ResponseEntity.badRequest().build();
        }
        chatService.removeParticipant(chatId, userId);
        userService.updateChatsForUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/participants/{chatId}")
    public ResponseEntity<List<Long>> getParticipants(@PathVariable @NonNull Long chatId) {
        List<Long> participants = chatService.getParticipants(chatId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/checkPassword/{chatId}")
    public ResponseEntity<Boolean> checkPassword(@PathVariable @NonNull Long chatId, @RequestBody @NonNull String password) {
        boolean isCorrect = chatService.checkPassword(chatId, password);
        return ResponseEntity.ok(isCorrect);
    }

    @PostMapping("/checkIfUserIsParticipant/{chatId}/{userId}")
    public ResponseEntity<Boolean> checkIfUserIsParticipant(@PathVariable @NonNull Long chatId, @PathVariable @NonNull Long userId) {
        boolean isParticipant = chatService.checkIfUserIsParticipant(chatId, userId);
        return ResponseEntity.ok(isParticipant);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDTO>> getChatsByUserId(@PathVariable @NonNull Long userId) {
        List<ChatDTO> chatDTOs = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chatDTOs);
    }
    
}

