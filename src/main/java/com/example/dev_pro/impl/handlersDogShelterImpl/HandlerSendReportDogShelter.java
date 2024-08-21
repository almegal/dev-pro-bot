package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.impl.handlersCatShelterImpl.HandlerSendReportCatShelter;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.service.SendReportService;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerSendReportDogShelter implements InputMessageHandlerDogShelter {

    TelegramBot telegramBot;
    SendReportService sendReportService;
    private static final Logger logger = LoggerFactory.getLogger(HandlerSendReportDogShelter.class);

    @Override
    public SendMessage handle(Message message) {

        logger.info("Вызван метод handle у класса HandlerSendReportDogShelter");
        Report report = new Report();
        Long chatId = message.chat().id();
        report = sendReportService.handleReport(message);
        if (report.getTextReport() == null) {
            SendMessage errorMessage = new SendMessage(chatId, "Нет поля text! Заполните и отправьте повторно");
            telegramBot.execute(errorMessage);
            logger.info("Ошибка поля text пустое");
            return errorMessage;
        }
        if (report.getFilePath() == null) {
            SendMessage errorMessage = new SendMessage(chatId, "Нет поля Photo! Заполните и отправьте повторно");
            telegramBot.execute(errorMessage);
            logger.info("Ошибка поля photo пустое");
            return errorMessage;
        }
        SendMessage replyMessage = new SendMessage(chatId, "Отчет успешно сохранен!");
        telegramBot.execute(replyMessage);
        logger.info("Отчет упешно сохранен!");
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.SEND_REPORT;
    }
}
