package com.example.dev_pro.component.impl;

import com.example.dev_pro.component.Buttons;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class ChoosingKeyboardButtons implements Buttons {
    @Override
    public Keyboard getButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton("Cat"),
                        new KeyboardButton("Dog")
                }
        ).resizeKeyboard(true);
    }

}
