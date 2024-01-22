package hskl.cnse.chat.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import hskl.cnse.chat.db.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Modifying
    @Query("DELETE FROM Message m WHERE m.id = :id")
    void deleteById(@NonNull Long id);
    @Modifying
    @Query("DELETE FROM Message m WHERE m.chat.id = :chat_id")
    void deleteByChat_Id(Long chat_id);
    List<Message> findByChat_Id(Long chat_id);
}
