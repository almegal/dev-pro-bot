package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;

public interface AdopterService {
    Adopter findByTelegramUserId(Long userId);
}
