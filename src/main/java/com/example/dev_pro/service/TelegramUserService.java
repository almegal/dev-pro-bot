package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.TelegramUser;

public interface TelegramUserService {
    TelegramUser getById(Long id);

    void save(TelegramUser user);

    void update(TelegramUser user);

    TelegramUser findTelegramUserByAdopter(Adopter adopter);

}
