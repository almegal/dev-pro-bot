package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
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
public class BotStateContextCatShelter {

    private Map<BotStateCatShelter, InputMessageHandlerCatShelter> messageHandlers = new HashMap<>();
    // ключ - текущее состояние бота, значение - обработчик для данного состояния

    public BotStateContextCatShelter(List<InputMessageHandlerCatShelter> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    /**
     * Метод принимает в качестве параметров текущее состояние бота и сообщение от пользователя, находит обработчика
     * сообщения в зависимости от текущего состояния бота и возвращает ответное сообщение бота
     * @param currentState текущее состояние бота
     * @param message сообщение от пользователя
     * @return ответное сообщение от бота
     */

    public SendMessage processInputMessage(BotStateCatShelter currentState, Message message) {
        InputMessageHandlerCatShelter currentMessageHandler = findMessageHandler(currentState);
        // поиск обработчика
        return currentMessageHandler.handle(message);
        // возвращение ботом ответного сообщения пользователю (вызывается метод из соответствующего класса - обработчика)
    }

    private InputMessageHandlerCatShelter findMessageHandler(BotStateCatShelter currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(BotStateCatShelter.FILLING_PROFILE);
            // Если состояние бота соответствует любому состоянию из перечисленных ниже, то возвращается обработчик из
            // класса HandlerFillingProfileCatShelter;
            // иначе возвращается обработчик из других классов
        }

        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotStateCatShelter currentState) {
        switch (currentState) {
            case ASK_PERSONAL_DATA:
            case FILLING_PROFILE:
            case PROFILE_FILLED:
                return true;
            default:
                return false;
        }
    }
}
