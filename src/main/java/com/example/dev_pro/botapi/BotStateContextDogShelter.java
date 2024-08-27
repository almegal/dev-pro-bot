package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
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
     * @param currentState текущее состояние бота
     * @param message сообщение от пользователя
     * @return ответное сообщение от бота
     */

    public SendMessage processInputMessage(BotStateDogShelter currentState, Message message) {
        InputMessageHandlerDogShelter currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandlerDogShelter findMessageHandler(BotStateDogShelter currentState) {
        if (currentState.equals(BotStateDogShelter.FILLING_PROFILE) ||
                currentState.equals(BotStateDogShelter.ASK_PERSONAL_DATA) ||
                currentState.equals(BotStateDogShelter.PROFILE_FILLED)) {
            return messageHandlers.get(BotStateDogShelter.FILLING_PROFILE);
        }
        if (currentState.equals(BotStateDogShelter.SEND_PHOTO_REPORT) ||
                currentState.equals(BotStateDogShelter.ASK_PET_ID_REPORT) ||
                currentState.equals(BotStateDogShelter.ASK_TEXT_REPORT) ||
                currentState.equals(BotStateDogShelter.ASK_PHOTO_REPORT) ||
                currentState.equals(BotStateDogShelter.PHOTO_UPLOADED)) {
            return messageHandlers.get(BotStateDogShelter.SEND_PHOTO_REPORT);
        }

        return messageHandlers.get(currentState);
        // Иначе возвращается обработчик из других классов
    }
}
