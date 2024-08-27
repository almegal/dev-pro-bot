package com.example.dev_pro.repository;

import com.example.dev_pro.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {

    /**
     * Метод по поиску усыновителя по идентификатору пользователя телеграм
     * @param telegramUserId идентификатор пользователя телеграм
     * @return объект Optional, содержащий усыновителя
     */
    Optional<Adopter> findAdopterByTelegramUserId(Long telegramUserId);
}
