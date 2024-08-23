package com.example.dev_pro.repository;

import com.example.dev_pro.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByTelegramId(Long id);

}
