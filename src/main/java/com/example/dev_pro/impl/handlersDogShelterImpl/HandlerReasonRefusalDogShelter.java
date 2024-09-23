package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerReasonRefusalDogShelter implements InputMessageHandlerDogShelter {

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
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.REASON_REFUSAL_COM;
    }
}
