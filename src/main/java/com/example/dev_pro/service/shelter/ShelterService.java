package com.example.dev_pro.service.shelter;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс ShelterService определяет методы для работы с командами и обновлениями,
 * связанными с приютом в Telegram боте.
 */
public interface ShelterService {

    /**
     * Устанавливает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации всех доступных команд бота.
     */
    void setCommands();

    /**
     * Обрабатывает команду, полученную от пользователя.
     *
     * @param text текст команды, введенной пользователем.
     * @return результат выполнения команды в виде строки.
     */
    String handleCommand(String text);

    /**
     * Обрабатывает обновление, полученное от Telegram Bot API.
     *
     * @param update объект Update, содержащий данные об обновлении, такие как данные сообщения, отправителя и т.д.
     */
    void handleUpdate(Update update);
}
