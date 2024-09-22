package com.example.dev_pro.service;

import com.example.dev_pro.model.TelegramUser;

public class DefaultProps {
    public static TelegramUser MOCK_USER = new TelegramUser();

    static {
        MOCK_USER.setId(0L);
        MOCK_USER.setShelter("Cat");
        MOCK_USER.setTelegramId(12345L);
    }
}
