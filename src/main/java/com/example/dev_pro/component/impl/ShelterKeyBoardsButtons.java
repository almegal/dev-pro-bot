package com.example.dev_pro.component.impl;

import com.example.dev_pro.component.Buttons;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;

 aleekky-feature
import java.awt.*;

@Component
public class ShelterKeyBoardsButtons implements Buttons {


@Component
public class ShelterKeyBoardsButtons implements Buttons {
 dev
    public static final String INFO_COM = "/info";
    public static final String TAKE_COM = "/take";
    public static final String REPORT_COM = "/report";
    public static final String CALL_COM = "/call";

 aleekky-feature
    public static final String OVERVIEW_COM = "/overview";
    public static final String ADDRESS_COM = "/address";
    public static final String CAR_PASS_COM = "/car_pass";
    public static final String SAFETY_RULES_COM = "/safety_rules";
    public static final String USER_CONTACT_COM = "/user_contact";
    public static final String COME_BACK_COM = "/come_back";


 dev
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
 aleekky-feature

    @Override
    public Keyboard getInfoKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[][]{
                        {new KeyboardButton(OVERVIEW_COM), new KeyboardButton(ADDRESS_COM),
                                new KeyboardButton(CAR_PASS_COM)},
                        {new KeyboardButton(SAFETY_RULES_COM), new KeyboardButton(USER_CONTACT_COM),
                                new KeyboardButton(COME_BACK_COM)}
                }
        ).resizeKeyboard(true);
    }

 dev
}
