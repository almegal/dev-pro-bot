package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс CommandHandlerService предоставляет методы для обработки команд,
 * полученных от пользователей через Telegram бота.
 */
public interface CommandHandlerService {

    /**
     * Обрабатывает команду, полученную от пользователя, и возвращает ответное сообщение.
     *
     * @param text текст команды, полученной от пользователя
     * @return ответное сообщение, которое будет отправлено пользователю
     */
    String handleCommand(String text);

    /**
     * Обрабатывает обновление, полученное от Telegram API, определяя и выполняя соответствующую команду.
     *
     * @param update объект Update, содержащий информацию об обновлении, полученном от Telegram API
     */
    void commandProcessing(Update update);

    /**
     * Отправляет сообщение в указанный чат.
     *
     * @param chatId идентификатор чата, в который необходимо отправить сообщение
     * @param msg    текст сообщения, которое необходимо отправить
     */
    void sendMsg(Long chatId, String msg);
}
