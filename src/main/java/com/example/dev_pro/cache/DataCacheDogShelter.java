package com.example.dev_pro.cache;

import com.example.dev_pro.botapi.BotStateDogShelter;

public interface DataCacheDogShelter extends DataCache {

    /**
     * Метод назначает для пользователя конкретное состояние бота
     * @param userId идентификатор пользователя, извлеченный из сообщения
     * @param botState состояние бота
     */
    void setUsersCurrentBotState(Long userId, BotStateDogShelter botState);


    /**
     * Метод возвращает по идентификатору пользователя текущее состояния бота
     *
     * @param userId идентификатор пользователя, извлеченный из сообщения
     * @return объект типа BotStateDogShelter - текущее состояние бота
     */
    BotStateDogShelter getUsersCurrentBotState(Long userId);


}
