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
public class HandlerOverviewCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - OVERVIEW_COM. В результате нажатия
     * пользователем на кнопку overview_com бот отправляет пользователю соответствующее сообщение.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getOverviewMsgCatShelter());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.OVERVIEW_COM;
    }

}
