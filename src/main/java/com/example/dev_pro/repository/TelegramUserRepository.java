package com.example.dev_pro.repository;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByTelegramId(Long id);

    Optional<TelegramUser> findById(Long id);

    Optional<TelegramUser> findTelegramUserByAdopter(Adopter adopter);

    List<TelegramUser> findAll();
}
