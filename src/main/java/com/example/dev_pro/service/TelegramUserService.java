package com.example.dev_pro.service;

import com.example.dev_pro.model.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {
    TelegramUser getById(Long id);

    void save(TelegramUser user);

    void update(TelegramUser user);

    Optional<TelegramUser> findById(Long userId);
}
