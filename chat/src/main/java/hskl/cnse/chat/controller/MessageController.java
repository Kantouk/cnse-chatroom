package hskl.cnse.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hskl.cnse.chat.db.model.Message;
import hskl.cnse.chat.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChat(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message sentMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(sentMessage);
    }

    @PostMapping("/edit")
    public ResponseEntity<Message> editMessage(@RequestBody Message message) {
        Message editedMessage = messageService.editMessage(message);
        return ResponseEntity.ok(editedMessage);
    }

    @PostMapping("/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteChatHistoryForUser/{chatId}")
    public ResponseEntity<Void> deleteChatHistoryForUser(@PathVariable Long chatId) {
        messageService.deleteChatHistoryForUser(chatId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteChatHistoryForAllUsers/{chatId}")
    public ResponseEntity<Void> deleteChatHistoryForAllUsers(@PathVariable Long chatId) {
        messageService.deleteChatHistoryForAllUsers(chatId);
        return ResponseEntity.ok().build();
    }

}

