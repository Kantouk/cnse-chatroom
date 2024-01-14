package hskl.cnse.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.model.*;
import hskl.cnse.chat.db.repositories.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByChatId(Long chat_id) {
        return messageRepository.findByChat_Id(chat_id);
    }

    public Message sendMessage(Message message) {
        // Nachricht senden
        return messageRepository.save(message);
    }

    public Message editMessage(Message message) {
        // Nachricht editieren
        return messageRepository.save(message);
    }

    public void deleteMessage(Long message_id) {
        // Nachricht löschen
        messageRepository.deleteById(message_id);
    }

    public void deleteChatHistoryForAllUsers(Long chat_id) {
        // Nachrichtenverlauf für alle Nutzer löschen
        messageRepository.deleteByChat_Id(chat_id);
    }
}
