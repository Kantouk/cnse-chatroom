package hskl.cnse.chat.db.dto;

import java.util.ArrayList;
import java.util.List;

import hskl.cnse.chat.db.model.AuthUser;


public class ChatCreationDto {
    private String name;
    private String password;
    private Long userId;
    private List<AuthUser> participants = new ArrayList<>();

    public ChatCreationDto() {
    }

    public ChatCreationDto(String name, String password, List<AuthUser> participants) {
        this.name = name;
        this.password = password;
        this.participants = participants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthUser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<AuthUser> participants) {
        this.participants = participants;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChatCreationDto [name=" + name + ", password=" + password + ", participants=" + participants.toString() + "]";
    }

}
