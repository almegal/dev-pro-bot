package com.example.dev_pro.service.handlers;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

/**
 * Обработчик сообщений
 */

public interface InputMessageHandler {

    /**
     * Метод по обработке сообщения от пользователя, поступившего в метод handleUpdate(Update update) классов
     * CatShelterServiceImpl и DogShelterServiceImpl (как в виде команды от кнопки, так и в виде текстового сообщения)
     * @param message сообщение от пользователя
     * @return ответное сообщение бота пользователю
     */

    SendMessage handle(Message message);
}
