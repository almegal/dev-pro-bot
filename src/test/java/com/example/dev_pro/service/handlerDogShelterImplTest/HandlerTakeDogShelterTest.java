package com.example.dev_pro.service.handlerDogShelterImplTest;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerTakeDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.Keyboard;
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
public class HandlerTakeDogShelterTest {

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterKeyBoardsButtons buttons;

    @InjectMocks
    private HandlerTakeDogShelter handler;

    private Message message;
    private Chat chat;
    private User user;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        chat = mock(Chat.class);
        user = mock(User.class);

        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(chat.id()).thenReturn(12345L);
        lenient().when(message.from()).thenReturn(user);
        lenient().when(user.id()).thenReturn(67890L);
    }

    @Test
    public void testHandle() {
        // Arrange
        String expectedMessage = "Take a cat from the shelter!";
        when(tBotConfig.getTakeMsg()).thenReturn(expectedMessage);
        Keyboard keyboard = mock(Keyboard.class);
        when(buttons.getTakeKeyboardButtons()).thenReturn(keyboard);

        // Act
        SendMessage actualSendMessage = handler.handle(message);

        // Assert
        assertEquals(12345L, actualSendMessage.getParameters().get("chat_id"));
        assertEquals(expectedMessage, actualSendMessage.getParameters().get("text"));
        assertEquals(keyboard, actualSendMessage.getParameters().get("reply_markup"));
        verify(telegramBot).execute(actualSendMessage);
    }

    @Test
    public void testGetHandlerName() {
        // Act
        BotStateDogShelter handlerName = handler.getHandlerName();

        // Assert
        assertEquals(BotStateDogShelter.TAKE_COM, handlerName);
    }
}