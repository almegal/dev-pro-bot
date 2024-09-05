package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.TelegramUser;

import java.util.List;

public interface TelegramUserService {

    /**
     * Метод по поиску пользователя по его идентификатору
     * @param id идентификатор пользователя
     * @return пользователя телеграм
     */
    TelegramUser getByUserId(Long id);

    /**
     * Метод по поиску пользователя по его идентификатору в мессенджере телеграм
     * @param id идентификатор пользователя в телеграм
     * @return пользователя телеграм
     */
    TelegramUser getById(Long id);

    void save(TelegramUser user);

    void update(TelegramUser user);

    TelegramUser findTelegramUserByAdopter(Adopter adopter);

    List<TelegramUser> getAll();

}
