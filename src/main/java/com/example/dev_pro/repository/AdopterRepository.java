package com.example.dev_pro.repository;

import com.example.dev_pro.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Adopter findByTelegramUserId(Long userId);

}
