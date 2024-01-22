package hskl.cnse.chat.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.ChatCreationDto;
import hskl.cnse.chat.db.dto.ChatDTO;
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
        chatRepository.deleteById(chatId);
    }

    public Chat getChatById(@NonNull Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

    public void addParticipant(@NonNull Long chatId, @NonNull Long userId) {
        if (checkIfUserIsParticipant(chatId, userId)) {
            logger.info("User ist bereits Teilnehmer!");
        } else {
            Chat chat = chatRepository.findById(chatId).orElse(null);
            AuthUser user = userRepository.findById(userId).orElse(null);
            chat.getParticipants().add(user);
            chatRepository.save(chat);
        }
    }

    public void removeParticipant(@NonNull Long chatId, @NonNull Long userId) {
        if (!checkIfUserIsParticipant(chatId, userId)) {
            logger.info("User ist kein Teilnehmer!");
        } else {
            Chat chat = chatRepository.findById(chatId).orElse(null);
            AuthUser user = userRepository.findById(userId).orElse(null);
            chat.getParticipants().remove(user);
            chatRepository.save(chat);
        }
    }

    public List<Long> getParticipants(@NonNull Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        List<Long> participants = chat.getParticipants().stream().map(AuthUser::getId).toList();
        return participants;
    }

    public boolean checkPassword(@NonNull Long chatId, @NonNull String password) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        return passwordEncoder.matches(password, chat.getPassword());
    }

    public boolean checkIfUserIsParticipant(@NonNull Long chatId, @NonNull Long userId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        AuthUser user = userRepository.findById(userId).orElse(null);
        return chat.getParticipants().contains(user);
    }

    public List<ChatDTO> getChatsByUserId(Long userId) {
        List<Chat> chats = chatRepository.findByUser_Id(userId);
        List<ChatDTO> chatDTOs = chats.stream().map(ChatDTO::new).toList();
        return chatDTOs;
    }

}
