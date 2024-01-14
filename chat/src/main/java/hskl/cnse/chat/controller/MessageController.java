package hskl.cnse.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hskl.cnse.chat.db.dto.MessageCreationData;
import hskl.cnse.chat.db.model.Message;
import hskl.cnse.chat.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<List<Message>> getMessagesByChat(@PathVariable Long chat_id) {
        List<Message> messages = messageService.getMessagesByChatId(chat_id);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageCreationData messageCreationData, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Message sentMessage = messageService.sendMessage(messageCreationData);
        return ResponseEntity.ok(sentMessage);
    }

    @PostMapping("/edit/{message_id}")
    public ResponseEntity<Message> editMessage(@RequestBody MessageCreationData messageCreationData, @PathVariable("message_id") @NonNull Long message_id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Message editedMessage = messageService.editMessage(messageCreationData, message_id);
        return ResponseEntity.ok(editedMessage);
    }

    @PostMapping("/delete/{message_id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable @NonNull Long message_id) {
        messageService.deleteMessage(message_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteChatHistoryForAllUsers/{chat_id}")
    public ResponseEntity<Void> deleteChatHistoryForAllUsers(@PathVariable @NonNull Long chat_id) {
        messageService.deleteChatHistoryForAllUsers(chat_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable @NonNull Long chatId) {
        Message message = messageService.getMessageById(chatId);
        return ResponseEntity.ok(message);
    }

}
