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
public class HandlerComeBackDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;


    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - COME_BACK_COM. В результате
     * нажатия пользователем на кнопку come_back_com бот отправляет пользователю сообщение "You have returned to the
     * main menu of the cat shelter" с прикрепленным к нему меню из четырех кнопок.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getComeBackMsgDogShelter());
        replyMessage.replyMarkup(buttons.getKeyboardButtons());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.COME_BACK_COM;
    }
}
