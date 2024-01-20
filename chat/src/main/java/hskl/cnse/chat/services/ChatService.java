package hskl.cnse.chat.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.ChatCreationDto;
import hskl.cnse.chat.db.model.AuthUser;
import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.db.repositories.ChatRepository;
import hskl.cnse.chat.db.repositories.UserRepository;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public List<Chat> getChatsByUserId(Long userId) {
        // Chats eines Nutzers abfragen
        return chatRepository.findByUser_Id(userId);
    }

    public Chat createChat(ChatCreationDto chatCreationDto) {
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("ChatService: createChat() wurde aufgerufen!");
        logger.info("ChatService: createChat() hat folgende Parameter erhalten: " + chatCreationDto.toString());
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
    
        AuthUser user = userRepository.findById(chatCreationDto.getUserId()).orElse(null);
        chatCreationDto.getParticipants().add(user);

        Chat chat = new Chat();
        chat.setName(chatCreationDto.getName());
        chat.setPassword(passwordEncoder.encode(chatCreationDto.getPassword()));
        chat.setParticipants(chatCreationDto.getParticipants());

        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("Chat " + chat.getName() + " wurde erfolgreich erstellt!");
        logger.info("Chat " + chat.getName() + " hat folgende Teilnehmer: " + chat.getParticipants().isEmpty());
        logger.info("Chat " + chat.getName() + " hat folgendes Passwort: " + chat.getPassword());
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");

        return chatRepository.save(chat);
    }

    public Chat renameChat(ChatCreationDto chatCreationDto, @NonNull Long chatId) {
        Chat chat = getChatById(chatId);
        chat.setName(chatCreationDto.getName());

        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("Chat " + chat.getName() + " wurde erfolgreich umbenannt!");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");

        return chatRepository.save(chat);
    }

    public void deleteChat(@NonNull Long chatId) {
        // Chat löschen
        chatRepository.deleteById(chatId);
    }

    public Chat getChatById(@NonNull Long chatId) {
        // Chat abfragen
        return chatRepository.findById(chatId).orElse(null);
    }

    public void addParticipant(@NonNull Long chatId, @NonNull Long userId) {
        // Teilnehmer hinzufügen
        Chat chat = chatRepository.findById(chatId).orElse(null);
        AuthUser user = userRepository.findById(userId).orElse(null);
        chat.getParticipants().add(user);
        chatRepository.save(chat);
    }

    public void removeParticipant(@NonNull Long chatId, @NonNull Long userId) {
        // Teilnehmer entfernen
        Chat chat = chatRepository.findById(chatId).orElse(null);
        AuthUser user = userRepository.findById(userId).orElse(null);
        chat.getParticipants().remove(user);
        chatRepository.save(chat);
    }

}

