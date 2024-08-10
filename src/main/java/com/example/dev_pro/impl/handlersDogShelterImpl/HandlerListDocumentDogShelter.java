package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerListDocumentDogShelter implements InputMessageHandlerDogShelter {
    private final TelegramBot telegramBot;
    private final TelegramBotConfiguration telegramBotConfiguration;

    @Override
    public SendMessage handle(Message message) {
        Long id = message.chat().id();
        SendMessage replyMessage = new SendMessage(id, telegramBotConfiguration.getDocumentForTakeAnimals());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.DOCUMENT_FOR_TAKE_ANIMAL_COM;
    }
}
