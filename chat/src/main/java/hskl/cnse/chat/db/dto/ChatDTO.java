package hskl.cnse.chat.db.dto;

import java.util.ArrayList;
import java.util.List;

import hskl.cnse.chat.db.model.AuthUser;
import hskl.cnse.chat.db.model.Chat;
import hskl.cnse.chat.db.model.Message;
import io.micrometer.common.lang.Nullable;

public class ChatDTO {

    private Long id;
    private String name;
    @Nullable
    private String password;
    private List<Long> messageIds = new ArrayList<>();
    private List<Long> participantIds = new ArrayList<>();

    public ChatDTO() {
    }

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getName();
        this.password = chat.getPassword();
        if (chat.getMessages() != null) {
            for (Message message : chat.getMessages()) {
                this.messageIds.add(message.getId());
            }
        }
        if (chat.getParticipants().size() > 0) {
            for (AuthUser user : chat.getParticipants()) {
                this.participantIds.add(user.getId());
            }
        }
    }

    public ChatDTO(String name) {
        this.name = name;
        this.password = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessages(List<Long> messageId) {
        this.messageIds = messageId;
    }

    public void setParticipants(List<Long> userIds) {
        this.participantIds = userIds;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void addParticipant(Long userId) {
        this.participantIds.add(userId);
    }

    public void removeParticipant(Long userId) {
        this.participantIds.remove(userId);
    }

    public boolean hasUser() {
        return this.participantIds.size() > 0;
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + ", name=" + name + ", password=" + password + ", messages=" + messageIds.toString()
                + ", participants=" + participantIds.toString() + "]";
    }

}
