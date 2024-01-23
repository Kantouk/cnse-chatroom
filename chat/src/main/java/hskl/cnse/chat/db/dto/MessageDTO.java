package hskl.cnse.chat.db.dto;

import java.time.LocalDateTime;

import hskl.cnse.chat.db.model.Message;

public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long chatId;
    private Long userId;

    public MessageDTO() {
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
        this.chatId = message.getChat() != null ? message.getChat().getId() : null;
        this.userId = message.getUser() != null ? message.getUser().getId() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(Long senderId) {
        this.userId = senderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChat(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", content=" + content + ", chat=" + chatId + ", user="
                + userId + "]";
    }

}
