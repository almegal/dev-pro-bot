package com.example.dev_pro.service.handlerCatShelterImplTest;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersCatShelterImpl.HandlerInfoCatShelter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HandlerInfoCatShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterKeyBoardsButtons buttons;

    @InjectMocks
    private HandlerInfoCatShelter handler;

    private Message message;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        chat = mock(Chat.class);

        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(chat.id()).thenReturn(456L);
    }

    @Test
    public void testHandle() {
        // Arrange
        when(tBotConfig.getInfoMsgCatShelter()).thenReturn("Please select the menu item!");
        when(buttons.getInfoKeyboardButtons()).thenReturn(null); // Mock the keyboard buttons if necessary

        // Act
        SendMessage response = handler.handle(message);

        // Assert
        assertEquals("Please select the menu item!", response.getParameters().get("text"));
        assertEquals(456L, response.getParameters().get("chat_id"));
        verify(telegramBot).execute(response);
    }

    @Test
    public void testGetHandlerName() {
        // Act
        BotStateCatShelter handlerName = handler.getHandlerName();

        // Assert
        assertEquals(BotStateCatShelter.INFO_COM, handlerName);
    }
}