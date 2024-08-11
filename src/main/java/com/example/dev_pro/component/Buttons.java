package com.example.dev_pro.component;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface Buttons {

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок главного меню бота конкретного приюта.
     */
    Keyboard getKeyboardButtons();

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок меню info бота конкретного приюта.
     */
    Keyboard getInfoKeyboardButtons();

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок меню take бота конкретного приюта.
     */
    Keyboard getTakeKeyboardButtons();

    /**
     * @return возвращает команды для взаимодействия с ботом.
     * Этот метод должен быть вызван для инициализации кнопок меню Recommendations бота конкретного приюта.
     */
    Keyboard getRecommendationsButtons();
}
