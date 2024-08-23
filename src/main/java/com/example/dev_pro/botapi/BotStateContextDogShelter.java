package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Поиск обработчика сообщений для каждого состояния бота
 */

@Component
public class BotStateContextDogShelter {

    private Map<BotStateDogShelter, InputMessageHandlerDogShelter> messageHandlers = new HashMap<>();

    public BotStateContextDogShelter(List<InputMessageHandlerDogShelter> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    /**
     * Метод принимает в качестве параметров текущее состояние бота и сообщение от пользователя, находит обработчика
     * сообщения в зависимости от текущего состояния бота и возвращает ответное сообщение бота
     *
     * @param currentState текущее состояние бота
     * @param message      сообщение от пользователя
     * @return ответное сообщение от бота
     */

    public SendMessage processInputMessage(BotStateDogShelter currentState, Message message) {
        InputMessageHandlerDogShelter currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandlerDogShelter findMessageHandler(BotStateDogShelter currentState) {
        if (currentState.equals(BotStateDogShelter.SEND_REPORT) ||
                currentState.equals(BotStateDogShelter.SEND_PHOTO) ||
                currentState.equals(BotStateDogShelter.SEND_TEXT) ||
                currentState.equals(BotStateDogShelter.PHOTO_TEXT_DOWNLOAD)

        ) {
            return messageHandlers.get(BotStateDogShelter.FILLING_PROFILE);
        }

        if (currentState.equals(BotStateDogShelter.FILLING_PROFILE) ||
                currentState.equals(BotStateDogShelter.ASK_PERSONAL_DATA) ||
                currentState.equals(BotStateDogShelter.PROFILE_FILLED)
        ) {
            return messageHandlers.get(currentState);
        }
        return messageHandlers.get(currentState);
    }
}
