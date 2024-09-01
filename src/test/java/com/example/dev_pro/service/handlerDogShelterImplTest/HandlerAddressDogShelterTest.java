package com.example.dev_pro.service.handlerDogShelterImplTest;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerAddressDogShelter;
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
public class HandlerAddressDogShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private HandlerAddressDogShelter handlerAddressDogShelter;

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
        when(tBotConfig.getAddressMsgDogShelter()).thenReturn(expectedAddressMessage);

        // Act
        SendMessage result = handlerAddressDogShelter.handle(message);

        // Assert
        verify(telegramBot).execute(result);
        assertEquals(expectedAddressMessage, result.getParameters().get("text"));
        assertEquals(123456789L, result.getParameters().get("chat_id"));
    }

    @Test
    public void testGetHandlerNameThenReturnsAddressCom() {
        // Act
        BotStateDogShelter result = handlerAddressDogShelter.getHandlerName();

        // Assert
        assertEquals(BotStateDogShelter.ADDRESS_COM, result);
    }
}