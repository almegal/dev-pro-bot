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
public class HandlerRecommendationsDogShelter implements InputMessageHandlerDogShelter {
    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;

    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота -
     * RECOMMENDATIONS_COM. В результате нажатия
     * пользователем на кнопку RECOMMENDATIONS_COM бот отправляет пользователю сообщение
     * с прикрепленным к нему меню из 7 кнопок.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю "You are in the recommendations menu,
     * please select recommendations."
     * с клавиатурой - меню /RECOMMENDATIONS_COM из 7 кнопок
     */
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getSelectRecommMsg());
        replyMessage.replyMarkup(buttons.getRecommendationsButtons());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.RECOMMENDATIONS_COM;
    }
}
