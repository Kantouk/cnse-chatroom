package hskl.cnse.chat.db.repositories;

import java.util.List;
import hskl.cnse.chat.db.model.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChat_Id(Long chat_id);
    void deleteByChat_Id(Long chat_id);
}
