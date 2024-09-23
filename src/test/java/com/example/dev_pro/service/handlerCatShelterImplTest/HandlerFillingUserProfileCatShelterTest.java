package com.example.dev_pro.service.handlerCatShelterImplTest;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.impl.handlersCatShelterImpl.HandlerFillingUserProfileCatShelter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.example.dev_pro.util.MessageUtil;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HandlerFillingUserProfileCatShelterTest {

    @Mock
    private UserDataCacheCatShelter userDataCache;

    @Mock
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private TelegramUserService telegramUserService;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private HandlerFillingUserProfileCatShelter handler;

    private Message message;
    private User user;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        message = mock(Message.class);
        user = mock(User.class);
        chat = mock(Chat.class);

        lenient().when(message.from()).thenReturn(user);
        lenient().when(message.chat()).thenReturn(chat);
        lenient().when(user.id()).thenReturn(123L);
        lenient().when(chat.id()).thenReturn(456L);
        lenient().when(chat.username()).thenReturn("testuser");
    }

    @Test
    public void testHandleWhenAskPersonalDataThenProfileFilled() {
        // Arrange
        when(userDataCache.getUsersCurrentBotState(123L)).thenReturn(BotStateCatShelter.ASK_PERSONAL_DATA);
        when(tBotConfig.getUserContactMsgCatShelter()).thenReturn("Please provide your personal data.");
        TelegramUser telegramUser = new TelegramUser();
        when(userDataCache.getTelegramUser(123L)).thenReturn(telegramUser);

        // Act
        SendMessage response = handler.handle(message);

        // Assert
        verify(userDataCache).setUsersCurrentBotState(123L, BotStateCatShelter.PROFILE_FILLED);
        assertEquals("Please provide your personal data.", response.getParameters().get("text"));
        assertEquals(456L, response.getParameters().get("chat_id"));
    }

    @Test
    public void testHandleWhenProfileFilledThenSuccessMessage() throws InvalidInputException {
        // Arrange
        when(userDataCache.getUsersCurrentBotState(123L)).thenReturn(BotStateCatShelter.PROFILE_FILLED);
        when(message.text()).thenReturn("User data");
        when(tBotConfig.getSuccessUserProfileMsgCatShelter()).thenReturn("Profile created successfully, %s!");
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setFirstName("John");
        when(userDataCache.getTelegramUser(123L)).thenReturn(telegramUser);

        // Mock the static method
        try (MockedStatic<MessageUtil> mockedStatic = Mockito.mockStatic(MessageUtil.class)) {
            mockedStatic.when(() -> MessageUtil.parseCreateCommand(telegramUser, "User data")).thenReturn(telegramUser);

            // Act
            SendMessage response = handler.handle(message);

            // Assert
            verify(telegramUserService).update(telegramUser);
            verify(userDataCache).setUsersCurrentBotState(123L, BotStateCatShelter.INFO_COM);
            assertEquals("Profile created successfully, John!", response.getParameters().get("text"));
            assertEquals(456L, response.getParameters().get("chat_id"));
        }
    }

    @Test
    public void testHandleWhenProfileFilledThenErrorMessage() throws InvalidInputException {
        // Arrange
        when(userDataCache.getUsersCurrentBotState(123L)).thenReturn(BotStateCatShelter.PROFILE_FILLED);
        when(message.text()).thenReturn("Invalid data");
        when(tBotConfig.getErrorMsg()).thenReturn("Error occurred.");
        TelegramUser telegramUser = new TelegramUser();
        when(userDataCache.getTelegramUser(123L)).thenReturn(telegramUser);

        // Mock the static method to throw an exception
        try (MockedStatic<MessageUtil> mockedStatic = Mockito.mockStatic(MessageUtil.class)) {
            mockedStatic.when(() -> MessageUtil.parseCreateCommand(telegramUser, "Invalid data"))
                    .thenThrow(new InvalidInputException("Invalid input"));

            // Act
            SendMessage response = handler.handle(message);

            // Assert
            verify(userDataCache).setUsersCurrentBotState(123L, BotStateCatShelter.INFO_COM);
            assertEquals("Error occurred.", response.getParameters().get("text"));
            assertEquals(456L, response.getParameters().get("chat_id"));
        }
    }

    @Test
    public void testGetHandlerName() {
        // Act
        BotStateCatShelter handlerName = handler.getHandlerName();

        // Assert
        assertEquals(BotStateCatShelter.FILLING_PROFILE, handlerName);
    }
}