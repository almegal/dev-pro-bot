package com.example.dev_pro.impl.handlersCatShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerReasonRefusalCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration telegramBotConfiguration;
    private final TelegramBot telegramBot;

    /**
     * Метод по обработке сообщения от пользователя, поступившего в метод handleUpdate(Update update) классов
     * CatShelterServiceImpl и DogShelterServiceImpl (как в виде команды от кнопки, так и в виде текстового сообщения)
     *
     * @param message сообщение от пользователя
     * @return ответное сообщение бота пользователю
     */
    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, telegramBotConfiguration.getReasonRefusal());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    /**
     * Метод возвращает имя обработчика, который будет обрабатывать поступившее от пользователя сообщение
     *
     * @return имя обработчика
     */
    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.REASON_REFUSAL_COM;
    }
}
