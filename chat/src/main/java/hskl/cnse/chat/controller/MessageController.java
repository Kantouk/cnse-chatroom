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
import hskl.cnse.chat.db.dto.MessageDTO;
import hskl.cnse.chat.db.model.Message;
import hskl.cnse.chat.services.MessageService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<List<MessageDTO>> getMessagesByChat(@PathVariable Long chat_id) {
        List<MessageDTO> messageDTOs = messageService.getMessagesByChatId(chat_id);
        return ResponseEntity.ok(messageDTOs);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageCreationData messageCreationData, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Message sentMessage = messageService.sendMessage(messageCreationData);
        MessageDTO rMessageDto = new MessageDTO(sentMessage);
        return ResponseEntity.ok(rMessageDto);
    }

    @PostMapping("/edit/{message_id}")
    public ResponseEntity<MessageDTO> editMessage(@RequestBody MessageCreationData messageCreationData, @PathVariable("message_id") @NonNull Long message_id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Message editedMessage = messageService.editMessage(messageCreationData, message_id);
        MessageDTO rMessageDto = new MessageDTO(editedMessage);
        return ResponseEntity.ok(rMessageDto);
    }

    @Transactional
    @PostMapping("/delete/{message_id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable @NonNull Long message_id) {
        messageService.deleteMessage(message_id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/deleteHistory/{chat_id}")
    public ResponseEntity<Void> deleteChatHistoryForAllUsers(@PathVariable @NonNull Long chat_id) {
        messageService.deleteChatHistoryForAllUsers(chat_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable @NonNull Long messageId) {
        Message message = messageService.getMessageById(messageId);
        MessageDTO rMessageDto = new MessageDTO(message);
        return ResponseEntity.ok(rMessageDto);
    }

}
