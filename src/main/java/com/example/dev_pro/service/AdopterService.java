package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;


public interface AdopterService {


    /**
     * Метод по поиску усыновителя по его идентификатору
     * @param id идентификатор усыновителя
     * @return объект Optional, содержащий усыновителя
     */

    Adopter findAdopterById(Long id);


    /**
     * Метод по поиску усыновителя по идентификатору пользователя телеграм
     * @param telegramUserId идентификатор пользователя телеграм
     * @return объект Optional, содержащий усыновителя
     */

    Adopter findAdopterByTelegramUserId(Long telegramUserId);
}
