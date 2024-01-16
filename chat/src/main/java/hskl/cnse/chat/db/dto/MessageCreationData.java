package hskl.cnse.chat.db.dto;


import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.db.model.User;

public class MessageCreationData {

    private String content;
    private Chat chat;
    private User user;

    public MessageCreationData() {
    }

    public MessageCreationData(String content, Chat chat, User user) {
        this.content = content;
        this.chat = chat;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
