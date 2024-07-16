package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.CommandHandlerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private final TelegramBot telegramBot;
    private final TelegramBotConfiguration telegramBotConfiguration;

    @Value("${telegram.bot.startMsg}")
    private String startMsg;

    @Value("${telegram.bot.infoMsg}")
    private String infoMsg;

    @Value("${telegram.bot.takeMsg}")
    private String takeMsg;

    private static final String START_COM = "/start";
    private static final String INFO_COM = "/info";
    private static final String TAKE_COM = "/take";
    private static final String REPORT_COM = "/report";
    private static final String CALL_VOLUNTEER = "Your request cannot be processed, I call volunteer.";

    /**
     * Метод обработка команды и возврат результата.
     * @param chatId идентификатор чата.
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    public String handleCommand(Long chatId, String text) {
        switch (text) {
            case START_COM:
                return startMsg;
            case INFO_COM:
                return infoMsg;
            case TAKE_COM:
                return takeMsg;
            case REPORT_COM:
                return null; // Добавим  процессе создание база данных!
            default:
                return CALL_VOLUNTEER;
        }
    }

    @Override
    public void commandProcessing(Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        String resultMsg = handleCommand(chatId, text);
        sendMsg(chatId, resultMsg);
    }

    /**
     * Метод отправки сообщения.
     * @param chatId идентификатор чата.
     * @param msg сообщение для отправки.
     */
    @Override
    public void sendMsg(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }


}
