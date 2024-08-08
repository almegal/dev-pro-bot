package com.example.dev_pro.impl.handlersCatShelterImpl;


import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerTakeCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - TAKE_COM. В результате
     * нажатия пользователем на кнопку take_com бот отправляет пользователю соответствующее сообщение с прикрепленным
     * к нему меню из семи кнопок.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getTakeMsg());
        replyMessage.replyMarkup(buttons.getTakeKeyboardButtons());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.TAKE_COM;
    }


}
