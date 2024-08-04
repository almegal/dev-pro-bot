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
public class HandlerCarPassDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - CAR_PASS_COM. В результате нажатия
     * пользователем на кнопку car_pass_com бот отправляет пользователю сообщение "To obtain a vehicle pass, contact
     * security of dog shelter. Contacts - +7-989-878-69-69".
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getCarPassMsgDogShelter());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.CAR_PASS_COM;
    }
}
