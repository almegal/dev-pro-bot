package com.example.dev_pro.service.handlers;

import com.example.dev_pro.botapi.BotStateCatShelter;

/**
 * Обработчик сообщений
 */
public interface InputMessageHandlerCatShelter extends InputMessageHandler {


    /**
     * Метод возвращает имя обработчика, который будет обрабатывать поступившее от пользователя сообщение
     * @return имя обработчика
     */

    BotStateCatShelter getHandlerName();
}
