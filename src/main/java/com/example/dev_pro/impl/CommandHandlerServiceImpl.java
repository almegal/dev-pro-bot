package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.CommandHandlerService;
import com.example.dev_pro.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private final TelegramBot telegramBot;
    private final TelegramBotConfiguration telegramBotConfiguration;
    private final VolunteerService volunteerService;

    @Value("${telegram.bot.startMsg}")
    private String startMsg;

    @Value("${telegram.bot.infoMsg}")
    private String infoMsg;

    @Value("${telegram.bot.takeMsg}")
    private String takeMsg;

    @Value("${telegram.bot.callVolunteerMsq}")
    private String callVolunteerMsq;

    @Value("${telegram.bot.errorMsg}")
    private String errorMsq;

    @Value("${telegram.bot.messageToVolunteerMsq}")
    private String messageToVolunteerMsq;

    private static final String START_COM = "/start";
    private static final String INFO_COM = "/info";
    private static final String TAKE_COM = "/take";
    private static final String REPORT_COM = "/report";
    private static final String CALL_VOLUNTEER = "/call";

    public String handleCommand(Long chatId, String text) {
        switch (text) {
            case START_COM:
                return startMsg;
            case INFO_COM:
                return infoMsg;
            case TAKE_COM:
                return takeMsg;
            case REPORT_COM:
                return null; // Добавим процессе создание база данных!
            case CALL_VOLUNTEER:
                return callVolunteerMsq;
            default:
                return errorMsq;
        }
    }

    /**
     * Данный метод извлекает из сообщения / команды пользователя идентификатор чата, никнейм и текст команды.
     * В результате вызова метода handleCommand() создается ответное сообщение пользователю от бота.
     * Если пользователь наберет команду /call, то в результате вызова методов из класса VolunteerServiceImpl
     * из базы данных будет получен список никнеймов волонтеров и отправлен пользователю для выбора конкретного
     * никнейма волонтера, и если пользователь выберет соответствующей командой никнейм конкретного пользователя,
     * то волонтеру будет отправлено сообщение о вызове и никнейм и идентификатор чата данного пользователя.
     * В отсутствие приведенных выше условий пользователю отправляется ранее созданное ответное сообщение.
     * @param update сообщение / команда пользователя
     */

    @Override
    public void commandProcessing(Update update) {
        Long chatId = update.message().chat().id();
        String userName = update.message().chat().username();
        String text = update.message().text();
        String resultMsg = handleCommand(chatId, text);

        if (text.equals(CALL_VOLUNTEER)) {
            List<String> listNickNamesVolunteers = volunteerService.getListNickNamesOfVolunteers();
            sendMsg(chatId, resultMsg + " " + listNickNamesVolunteers);

            boolean isResult = volunteerService.isSelectVolunteer(text);
            if (isResult) {
                Long chatIdVolunteer = volunteerService.getChatIdOfVolunteer(text);
                sendMsg(chatIdVolunteer, String.format(messageToVolunteerMsq, userName, chatId));
            }
        } else {
            sendMsg(chatId, resultMsg);
        }
    }


    @Override
    public void sendMsg(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }

}
