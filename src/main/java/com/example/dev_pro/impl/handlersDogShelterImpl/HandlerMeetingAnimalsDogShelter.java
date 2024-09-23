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
public class HandlerMeetingAnimalsDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration telegramBotConfiguration;
    private final TelegramBot telegramBot;

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, telegramBotConfiguration.getMeetingRulesForDog());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.RULES_FOR_ANIMAL;
    }
}
