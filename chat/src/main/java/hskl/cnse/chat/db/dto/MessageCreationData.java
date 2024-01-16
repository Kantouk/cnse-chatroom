package hskl.cnse.chat.db.dto;

public class MessageCreationData {

    private String content;
    private Long chat_id;
    private Long user_id;

    public MessageCreationData() {
    }

    public MessageCreationData(String content, Long chat_id, Long user_id) {
        this.content = content;
        this.chat_id = chat_id;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat(Long chat_id) {
        this.chat_id = chat_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "MessageCreationData [content=" + content + ", chat_id=" + chat_id + ", user_id=" + user_id + "]";
    }

}
