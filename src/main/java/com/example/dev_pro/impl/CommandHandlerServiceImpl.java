package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.CommandHandlerService;
import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private final TelegramBotConfiguration telegramBotConfiguration;
    private static final String START_COM = "/start";
    private static final String INFO_COM = "/info";
    private static final String TAKE_COM = "/take";
    private static final String REPORT_COM = "/report";
    private static final String CALL_VOLLONTER = "Your request cannot be processed, I call vollonter.";
    public String handleCommand(Long chatId, String text) {
        switch (text) {
            case START_COM:
                return telegramBotConfiguration.getStartMsg();
            case INFO_COM:
                return telegramBotConfiguration.getInfoMsg();
            case TAKE_COM:
                return telegramBotConfiguration.getTakeMsg();
            case REPORT_COM:
                return null; // Добавим  процессе создание база данных!
            default:
                return CALL_VOLLONTER;
        }
    }


}
