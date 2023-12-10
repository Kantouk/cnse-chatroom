package hskl.cnse.chat.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import hskl.cnse.chat.db.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUserId(Long userId);
}
