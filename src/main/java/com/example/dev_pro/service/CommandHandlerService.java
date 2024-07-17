package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Update;

public interface CommandHandlerService {

    String handleCommand(Long chatId, String text);

    void commandProcessing(Update update);

    void sendMsg(Long chatId, String msg);
}
