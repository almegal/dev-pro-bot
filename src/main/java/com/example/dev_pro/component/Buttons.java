package com.example.dev_pro.component;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface Buttons {
    public static String CAT_BUTTON = "Cat";
    public static String DOG_BUTTON = "Dog";

    public Keyboard getButtons();
}
