package hskl.cnse.chat.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hskl.cnse.chat.db.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.id = :user_id")
    List<Chat> findByUser_Id(@Param("user_id") Long user_id);
}
