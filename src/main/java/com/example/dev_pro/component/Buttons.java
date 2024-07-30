package com.example.dev_pro.component;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface Buttons {

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок главного меню бота конкретного приюта.
     */
    public Keyboard getKeyboardButtons();

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок меню info бота конкретного приюта.
     */

    public Keyboard getInfoKeyboardButtons();
}
