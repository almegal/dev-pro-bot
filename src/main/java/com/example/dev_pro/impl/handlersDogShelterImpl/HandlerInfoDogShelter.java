package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerInfoDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - INFO_COM. В результате нажатия
     * пользователем на кнопку info_com бот отправляет пользователю сообщение с прикрепленным к нему меню
     * из шести кнопок.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю "Please select the menu item!" с клавиатурой - меню /info из шести кнопок
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getInfoMsgDogShelter());
        replyMessage.replyMarkup(buttons.getInfoKeyboardButtons());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.INFO_COM;
    }


}
