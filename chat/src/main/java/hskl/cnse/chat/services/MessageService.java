package hskl.cnse.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.MessageCreationData;
import hskl.cnse.chat.db.model.*;
import hskl.cnse.chat.db.repositories.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByChatId(Long chat_id) {
        return messageRepository.findByChat_Id(chat_id);
    }

    public Message sendMessage(MessageCreationData messagecCreationData) {
        Message message = new Message();
        message.setContent(messagecCreationData.getContent());
        message.setChat(messagecCreationData.getChat());
        message.setUser(messagecCreationData.getUser());

        return messageRepository.save(message);
    }

    public Message editMessage(MessageCreationData messageCreationData, @NonNull Long message_id) {
        Message message = getMessageById(message_id);
        message.setContent(messageCreationData.getContent());
        
        return messageRepository.save(message);
    }

    public Message getMessageById(@NonNull Long message_id) {
        return messageRepository.findById(message_id).orElse(null);
    }

    public void deleteMessage(@NonNull Long message_id) {
        messageRepository.deleteById(message_id);
    }

    public void deleteChatHistoryForAllUsers(@NonNull Long chat_id) {
        // Nachrichtenverlauf für alle Nutzer löschen
        messageRepository.deleteByChat_Id(chat_id);
    }
}
