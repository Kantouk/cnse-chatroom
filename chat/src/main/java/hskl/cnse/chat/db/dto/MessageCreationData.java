package hskl.cnse.chat.db.dto;


import hskl.cnse.chat.db.model.AuthUser;
import hskl.cnse.chat.db.model.Chat;

public class MessageCreationData {

    private String content;
    private Chat chat;
    private AuthUser user;

    public MessageCreationData() {
    }

    public MessageCreationData(String content, Chat chat, AuthUser user) {
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

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

}
