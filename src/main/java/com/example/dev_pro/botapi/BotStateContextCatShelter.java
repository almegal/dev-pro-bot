package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Поиск обработчика сообщений для каждого состояния бота
 */

@Component
public class BotStateContextCatShelter {

    private static final Logger logger = LoggerFactory.getLogger(BotStateContextCatShelter.class);

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
        if (currentMessageHandler == null) {
            logger.error("No handler found for state: {}", currentState);
            return new SendMessage(message.chat().id(), "An error occurred. Please try again later.");
        }
        return currentMessageHandler.handle(message);
        // возвращение ботом ответного сообщения пользователю (вызывается метод из соответствующего класса - обработчика)
    }

    private InputMessageHandlerCatShelter findMessageHandler(BotStateCatShelter currentState) {
        if (currentState.equals(BotStateCatShelter.FILLING_PROFILE) ||
                currentState.equals(BotStateCatShelter.ASK_PERSONAL_DATA) ||
                currentState.equals(BotStateCatShelter.PROFILE_FILLED)) {
            return messageHandlers.get(BotStateCatShelter.FILLING_PROFILE);
        }
        if (currentState.equals(BotStateCatShelter.SEND_PHOTO_REPORT) ||
                currentState.equals(BotStateCatShelter.ASK_PET_ID_REPORT) ||
                currentState.equals(BotStateCatShelter.ASK_TEXT_REPORT) ||
                currentState.equals(BotStateCatShelter.ASK_PHOTO_REPORT) ||
                currentState.equals(BotStateCatShelter.PHOTO_UPLOADED)) {
            return messageHandlers.get(BotStateCatShelter.SEND_PHOTO_REPORT);
        }

        return messageHandlers.get(currentState);
        // Иначе возвращается обработчик из других классов
    }

}