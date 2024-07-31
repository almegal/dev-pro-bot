package com.example.dev_pro.component.impl;

import com.example.dev_pro.component.Buttons;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class ShelterKeyBoardsButtons implements Buttons {
    public static final String INFO_COM = "/info";
    public static final String TAKE_COM = "/take";
    public static final String REPORT_COM = "/report";
    public static final String CALL_COM = "/call";

    @Override
    public Keyboard getKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(INFO_COM),
                        new KeyboardButton(TAKE_COM),
                        new KeyboardButton(REPORT_COM),
                        new KeyboardButton(CALL_COM)
                }
        ).resizeKeyboard(true);
    }
}
