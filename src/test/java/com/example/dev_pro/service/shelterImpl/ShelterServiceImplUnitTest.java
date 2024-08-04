package com.example.dev_pro.service.shelterImpl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.shelter.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
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
public abstract class ShelterServiceImplUnitTest {
    @Mock
    public static Update UPDATE_MOCK;
    @Mock
    public static Message MESSAGE_MOCK;
    @Mock
    public static Chat CHAT_MOCK;
    //
    protected ShelterService service;
    @Mock
    private TelegramBot bot;
    @Spy
    private TelegramBotConfiguration tBotConfig;

    @BeforeEach
    protected abstract void init();


    @Test
    @DisplayName("Отправка сообщения пользователю при корректных значениях")
    public void botShouldExecuteWithCorrectMessage() {
        // настройка поведения моков
        when(UPDATE_MOCK.message()).thenReturn(MESSAGE_MOCK);
        when(MESSAGE_MOCK.chat()).thenReturn(CHAT_MOCK);
        when(CHAT_MOCK.id()).thenReturn(1L);
        when(MESSAGE_MOCK.text()).thenReturn("anyString()");

        // Вызов тестируемого метода
        service.handleUpdate(UPDATE_MOCK);

        // проверяем что идет вызов отправки сообщений
        verify(bot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    @DisplayName("Не должен вызываться метод отпрвки сообщения при иключнии")
    public void shouldReturnExceptionAndNeverSendMessage() {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            service.handleUpdate(null);
        });
        verify(bot, times(0)).execute(any(SendMessage.class));
    }
}
