package com.example.dev_pro.service.shelter;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;

/**
 * Интерфейс ShelterService определяет методы для работы с командами и обновлениями,
 * связанными с приютом в Telegram боте.
 */
public interface ShelterService {

    /**
     * Обрабатывает обновление, полученное от Telegram Bot API.
     *
     * @param update объект Update, содержащий данные об обновлении, такие как данные сообщения, отправителя и т.д.
     */
    void handleUpdate(Update update);
}
