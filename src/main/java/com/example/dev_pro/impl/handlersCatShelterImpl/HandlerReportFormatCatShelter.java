package com.example.dev_pro.impl.handlersCatShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerReportFormatCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(HandlerReportFormatCatShelter.class);

    @Override
    public SendMessage handle(Message message) {
        logger.info("Вызван метод handle у класса  HandlerReportFormatCatShelter ");
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getReportInstructionsMsg());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.REPORT_FORMAT;
    }
}
