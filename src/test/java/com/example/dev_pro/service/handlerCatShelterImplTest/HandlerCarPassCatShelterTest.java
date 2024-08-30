package com.example.dev_pro.service.handlerCatShelterImplTest;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersCatShelterImpl.HandlerCarPassCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HandlerCarPassCatShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private HandlerCarPassCatShelter handlerCarPassCatShelter;

    private Message message;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        chat = mock(Chat.class);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123456789L);
    }

    @Test
    public void testHandleWhenCalledThenSendsCarPassMessage() {
        // Arrange
        String expectedCarPassMessage = "To obtain a vehicle pass, contact security of cat shelter. Contacts - +7-985-879-69-69";
        when(tBotConfig.getCarPassMsgCatShelter()).thenReturn(expectedCarPassMessage);

        // Act
        SendMessage result = handlerCarPassCatShelter.handle(message);

        // Assert
        verify(telegramBot).execute(result);
        assertEquals(expectedCarPassMessage, result.getParameters().get("text"));
        assertEquals(123456789L, result.getParameters().get("chat_id"));
    }

    @Test
    public void testGetHandlerNameThenReturnsCarPassCom() {
        // Act
        BotStateCatShelter result = handlerCarPassCatShelter.getHandlerName();

        // Assert
        assertEquals(BotStateCatShelter.CAR_PASS_COM, result);
    }
}