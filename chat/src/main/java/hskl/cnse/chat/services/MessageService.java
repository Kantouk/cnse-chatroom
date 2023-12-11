package hskl.cnse.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.model.Message;
import hskl.cnse.chat.db.repositories.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    public Message sendMessage(Message message) {
        // Nachricht senden
        return messageRepository.save(message);
    }

    public Message editMessage(Message message) {
        // Nachricht editieren
        return messageRepository.save(message);
    }

    public void deleteMessage(Long messageId) {
        // Nachricht löschen
        messageRepository.deleteById(messageId);
    }

    public void deleteChatHistoryForUser(Long chatId) {
        // Nachrichtenverlauf löschen
        messageRepository.deleteByUserId(chatId);
    }

    public void deleteChatHistoryForAllUsers(Long chatId) {
        // Nachrichtenverlauf für alle Nutzer löschen
        messageRepository.deleteByChatId(chatId);
    }
}
