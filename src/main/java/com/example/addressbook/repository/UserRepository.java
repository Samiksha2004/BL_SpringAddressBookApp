package com.example.addressbook.repository;

import com.example.addressbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.addressbook.model.PasswordResetToken;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

}
