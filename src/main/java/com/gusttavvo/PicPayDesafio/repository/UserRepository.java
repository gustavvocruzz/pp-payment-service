package com.gusttavvo.PicPayDesafio.repository;

import com.gusttavvo.PicPayDesafio.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);

}
