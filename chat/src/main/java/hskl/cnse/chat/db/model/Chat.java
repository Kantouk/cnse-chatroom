package hskl.cnse.chat.db.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @JsonBackReference
    @ManyToMany
    private List<AuthUser> participants;

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

    public void setParticipants(List<AuthUser> users) {
        this.participants = users;
    }

    public List<AuthUser> getParticipants() {
        return participants;
    }

    public void addParticipant(AuthUser user) {
        this.participants.add(user);
    }

    public void removeParticipant(AuthUser user) {
        this.participants.remove(user);
    }

    public boolean hasUser(AuthUser user) {
        return this.participants.contains(user);
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + ", name=" + name + ", password=" + password + ", messages=" + messages
                + ", participants=" + participants + "]";
    }


}
