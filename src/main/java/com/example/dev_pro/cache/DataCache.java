package com.example.dev_pro.cache;

import com.example.dev_pro.model.TelegramUser;


public interface DataCache {

    /**
     * Метод по сохранению пользователя в словарь "telegramUsers"
     * @param userId идентификатор пользователя, извлеченный из его сообщения
     * @param telegramUser пользователь телеграм
     */
    void saveTelegramUser(Long userId, TelegramUser telegramUser);


    /**
     * Метод по получению из словаря "telegramUsers" пользователя по идентификатору, извлеченному из его сообщения
     * @param userId идентификатор пользователя
     * @return пользователя
     */
    TelegramUser getTelegramUser(Long userId);

}
