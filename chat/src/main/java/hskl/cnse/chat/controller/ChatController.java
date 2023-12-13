package hskl.cnse.chat.controller;

import org.springframework.web.bind.annotation.RestController;

import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.services.ChatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getChatsByUser(@PathVariable Long userId) {
        List<Chat> chats = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.ok(createdChat);
    }

    @PostMapping("/rename")
    public ResponseEntity<Chat> renameChat(@RequestBody Chat chat) {
        Chat renamedChat = chatService.renameChat(chat);
        return ResponseEntity.ok(renamedChat);
    }

    @PostMapping("/delete/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        return ResponseEntity.ok(chat);
    }

    /*@PostMapping("/addParticipant/{chatId}/{userId}")
    public ResponseEntity<Void> addParticipant(@PathVariable Long chatId, @PathVariable Long userId) {
        chatService.addParticipant(chatId, userId);
        return ResponseEntity.ok().build();
    }*/

    /*@PostMapping("/removeParticipant/{chatId}/{userId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable Long chatId, @PathVariable Long userId) {
        chatService.removeParticipant(chatId, userId);
        return ResponseEntity.ok().build();
    }*/

    
}

