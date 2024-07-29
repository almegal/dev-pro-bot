package com.example.dev_pro.component;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface Buttons {
    /**
     * Возращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации всех доступных кнопок бота конректного приюта.
     */
    public Keyboard getKeyboardButtons();
}
