package hskl.cnse.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.db.repositories.ChatRepository;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChatsByUserId(Long userId) {
        // Chats eines Nutzers abfragen
        return chatRepository.findByUserId(userId);
    }

    public Chat createChat(Chat chat) {
        // Chat erstellen
        return chatRepository.save(chat);
    }

    public Chat renameChat(Chat chat) {
        // Rename
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        // Chat löschen
        chatRepository.deleteById(chatId);
    }

    public Chat getChatById(Long chatId) {
        // Chat abfragen
        return chatRepository.findById(chatId).orElse(null);
    }

    /*public void addParticipant(Long chatId, Long userId) {
        // Teilnehmer hinzufügen
        Chat chat = chatRepository.findById(chatId).orElse(null);
        chat.getParticipants().add(userId);
        chatRepository.save(chat);
    }

    public void removeParticipant(Long chatId, Long userId) {
        // Teilnehmer entfernen
        Chat chat = chatRepository.findById(chatId).orElse(null);
        chat.getParticipants().remove(userId);
        chatRepository.save(chat);
    }*/

}

