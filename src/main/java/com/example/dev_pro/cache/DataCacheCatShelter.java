package com.example.dev_pro.cache;

import com.example.dev_pro.botapi.BotStateCatShelter;

public interface DataCacheCatShelter extends DataCache {

    /**
     * Метод по установлению для пользователя конкретного состояния бота и сохранению его в словарь "usersBotStates"
     * @param userId идентификатор пользователя, извлеченный из сообщения
     * @param botState состояние бота
     */
    void setUsersCurrentBotState(Long userId, BotStateCatShelter botState);


    /**
     * Метод по получению по идентификатору пользователя текущего состояния бота из словаря "usersBotStates"
     * @param userId идентификатор пользователя, извлеченный из сообщения
     * @return объект типа BotStateCatShelter - текущее состояние бота
     */
    BotStateCatShelter getUsersCurrentBotState(Long userId);


}
