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
public class HandlerMeetingAnimalsCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration telegramBotConfiguration;
    private final TelegramBot telegramBot;

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, telegramBotConfiguration.getMeetingRulesForCat());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.RULES_FOR_ANIMAL;
    }
}
