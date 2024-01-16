package hskl.cnse.chat.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hskl.cnse.chat.db.model.AuthUser;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByEmail(String email);
}

