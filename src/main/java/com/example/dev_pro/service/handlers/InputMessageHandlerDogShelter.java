package com.example.dev_pro.service.handlers;


import com.example.dev_pro.botapi.BotStateDogShelter;

/**
 * Обработчик сообщений
 */
public interface InputMessageHandlerDogShelter extends InputMessageHandler {


    /**
     * Метод возвращает имя обработчика, который будет обрабатывать поступившее от пользователя сообщение
     *
     * @return имя обработчика
     */

    BotStateDogShelter getHandlerName();
}
