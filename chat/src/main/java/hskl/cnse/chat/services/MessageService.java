package hskl.cnse.chat.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.MessageCreationData;
import hskl.cnse.chat.db.model.*;
import hskl.cnse.chat.db.repositories.ChatRepository;
import hskl.cnse.chat.db.repositories.MessageRepository;
import hskl.cnse.chat.db.repositories.UserRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public List<Message> getMessagesByChatId(Long chat_id) {
        return messageRepository.findByChat_Id(chat_id);
    }

    public Message sendMessage(MessageCreationData messageCreationData) {
        logger.info("*************************************************************");
        logger.info("*************************************************************");
        logger.info("MessageService.sendMessage() called");
        logger.info("MessageCreationData: " + messageCreationData.toString());
        logger.info("*************************************************************");
        logger.info("*************************************************************");
        Message message = new Message();
        message.setContent(messageCreationData.getContent());
        Chat chat = chatRepository.findById(messageCreationData.getChat_id()).orElse(null);
        message.setChat(chat);
        AuthUser user = userRepository.findById(messageCreationData.getUser_id()).orElse(null);
        message.setUser(user);
        logger.info("Message: " + message.toString());
        logger.info("*************************************************************");
        logger.info("*************************************************************");

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
