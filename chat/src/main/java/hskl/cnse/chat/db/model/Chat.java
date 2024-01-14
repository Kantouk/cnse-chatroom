package hskl.cnse.chat.db.model;

import java.util.List;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Nullable
    private String password;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @ManyToMany
    private List<User> participants;

    public Chat() {
    }

    public Chat(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Chat(String name) {
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> message) {
        this.messages = message;
    }

    public void setParticipants(List<User> users) {
        this.participants = users;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }

    public void removeParticipant(User user) {
        this.participants.remove(user);
    }

    public boolean hasUser(User user) {
        return this.participants.contains(user);
    }

}
