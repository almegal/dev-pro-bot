package com.example.dev_pro.service.shelterImpl;

import com.example.dev_pro.botapi.BotStateContextCatShelter;
import com.example.dev_pro.cache.DataCache;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.shelter.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class ShelterServiceImplTest {
    @Mock
    public static Update UPDATE_MOCK;
    @Mock
    public static Message MESSAGE_MOCK;
    @Mock
    public static Chat CHAT_MOCK;
    @Mock
    public static User USER_MOCK;
    //
    protected ShelterService service;
    @Mock
    private TelegramBot bot;
    @Spy
    private TelegramBotConfiguration tBotConfig;

    @BeforeEach
    protected abstract void init();

}
