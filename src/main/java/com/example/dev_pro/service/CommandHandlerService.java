package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Update;

public interface CommandHandlerService {
    /**
     * Метод обработка команды и возврат результата.
     * @param chatId идентификатор чата.
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    String handleCommand(Long chatId, String text);

    void commandProcessing(Update update);
    /**
     * Метод отправки сообщения.
     * @param chatId идентификатор чата.
     * @param msg сообщение для отправки.
     */
    void sendMsg(Long chatId, String msg);
}
