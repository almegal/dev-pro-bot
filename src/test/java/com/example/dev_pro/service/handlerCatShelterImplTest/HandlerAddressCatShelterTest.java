package com.example.dev_pro.service.handlerCatShelterImplTest;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersCatShelterImpl.HandlerAddressCatShelter;
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
public class HandlerAddressCatShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private HandlerAddressCatShelter handlerAddressCatShelter;

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
    public void testHandleWhenCalledThenSendsAddressMessage() {
        // Arrange
        String expectedAddressMessage = "This is the address message";
        when(tBotConfig.getAddressMsgCatShelter()).thenReturn(expectedAddressMessage);

        // Act
        SendMessage result = handlerAddressCatShelter.handle(message);

        // Assert
        verify(telegramBot).execute(result);
        assertEquals(expectedAddressMessage, result.getParameters().get("text"));
        assertEquals(123456789L, result.getParameters().get("chat_id"));
    }

    @Test
    public void testGetHandlerNameThenReturnsAddressCom() {
        // Act
        BotStateCatShelter result = handlerAddressCatShelter.getHandlerName();

        // Assert
        assertEquals(BotStateCatShelter.ADDRESS_COM, result);
    }
}