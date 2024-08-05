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
public class HandlerAddressDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - ADDRESS_COM. В результате нажатия
     * пользователем на кнопку address_com бот отправляет пользователю соответствующее сообщение и файл с изображением
     * - схемой проезда до приюта.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getAddressMsgDogShelter());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.ADDRESS_COM;
    }
}
