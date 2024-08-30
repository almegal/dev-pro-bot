package com.example.dev_pro.service.handlerDogShelterImplTest;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerReportFormatDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HandlerReportFormatDogShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private HandlerReportFormatDogShelter handler;

    private Message message;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        chat = mock(Chat.class);

        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(chat.id()).thenReturn(12345L);
    }

    @Test
    public void testHandle() {
        // Arrange
        String expectedMessage = "Report instructions message.";
        when(tBotConfig.getReportInstructionsMsg()).thenReturn(expectedMessage);

        // Act
        SendMessage response = handler.handle(message);

        // Assert
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage capturedMessage = argumentCaptor.getValue();

        assertEquals(12345L, capturedMessage.getParameters().get("chat_id"));
        assertEquals(expectedMessage, capturedMessage.getParameters().get("text"));
        assertEquals(expectedMessage, response.getParameters().get("text"));
        assertEquals(12345L, response.getParameters().get("chat_id"));
    }

    @Test
    public void testGetHandlerName() {
        // Act
        BotStateDogShelter handlerName = handler.getHandlerName();

        // Assert
        assertEquals(BotStateDogShelter.REPORT_FORMAT, handlerName);
    }
}