package com.example.dev_pro.component.impl;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class ChoosingKeyboardButtons {

    public static String CAT_BUTTON = "Cat";
    public static String DOG_BUTTON = "Dog";


    public Keyboard getKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Cat"),
                        new KeyboardButton("Dog"),
                }
        ).resizeKeyboard(true);
    }
}
