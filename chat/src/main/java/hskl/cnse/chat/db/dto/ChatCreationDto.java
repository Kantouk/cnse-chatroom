package hskl.cnse.chat.db.dto;

import java.util.List;

import hskl.cnse.chat.db.model.User;


public class ChatCreationDto {
    
    private String name;
    private String password;
    private List<User> participants;

    public ChatCreationDto() {
    }

    public ChatCreationDto(String name, String password, List<User> participants) {
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    
}
