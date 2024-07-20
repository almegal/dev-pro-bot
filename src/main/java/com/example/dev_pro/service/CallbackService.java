package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс CallbackService предоставляет метод для обработки обратных вызовов от Telegram Bot API.
 * Этот метод вызывается, когда бот получает обновление с обратным вызовом, например, нажатие кнопки в интерфейсе чата.
 */
public interface CallbackService {

    /**
     * Обрабатывает обновление с обратным вызовом от Telegram Bot API.
     *
     * @param update объект Update, содержащий данные об обратном вызове, такие как данные сообщения, отправителя и т.д.
     */
    void handleCallback(Update update);
}
