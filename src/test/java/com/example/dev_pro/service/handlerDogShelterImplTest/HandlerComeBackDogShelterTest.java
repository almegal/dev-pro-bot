package com.example.dev_pro.service.handlerDogShelterImplTest;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerComeBackDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
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
public class HandlerComeBackDogShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterKeyBoardsButtons buttons;

    @InjectMocks
    private HandlerComeBackDogShelter handler;

    private Message message;
    private Chat chat;
    private User user;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        chat = mock(Chat.class);
        user = mock(User.class);

        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(chat.id()).thenReturn(456L);
    }

    @Test
    public void testHandle() {
        // Arrange
        when(tBotConfig.getComeBackMsgDogShelter()).thenReturn("You have returned to the main menu of the cat shelter");
        when(buttons.getKeyboardButtons()).thenReturn(null); // Mock the keyboard buttons if necessary

        // Act
        SendMessage response = handler.handle(message);

        // Assert
        assertEquals("You have returned to the main menu of the cat shelter", response.getParameters().get("text"));
        assertEquals(456L, response.getParameters().get("chat_id"));
        verify(telegramBot).execute(response);
    }

    @Test
    public void testGetHandlerName() {
        // Act
        BotStateDogShelter handlerName = handler.getHandlerName();

        // Assert
        assertEquals(BotStateDogShelter.COME_BACK_COM, handlerName);
    }
}