package com.example.dev_pro.cache;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserDataCacheDogShelterTest {

    @Mock
    private TelegramUserService telegramUserService;

    @InjectMocks
    private UserDataCacheDogShelter userDataCacheDogShelter;

    private final Long userId = 1L;
    private TelegramUser mockTelegramUser;
    private BotStateDogShelter mockBotState;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTelegramUser = new TelegramUser();
        mockTelegramUser.setId(userId);
        mockTelegramUser.setTelegramId(12345L);
        mockTelegramUser.setChatId(67890L);
        mockTelegramUser.setNickName("testUser");
        mockTelegramUser.setFirstName("Test");
        mockTelegramUser.setLastName("User");

        mockBotState = BotStateDogShelter.FILLING_PROFILE;
    }

    @Test
    public void testSetUsersCurrentBotState() {
        // Act
        userDataCacheDogShelter.setUsersCurrentBotState(userId, mockBotState);

        // Assert
        assertEquals(mockBotState, userDataCacheDogShelter.getUsersCurrentBotState(userId));
    }

    @Test
    public void testGetUsersCurrentBotStateWhenStateNotSet() {
        // Act
        BotStateDogShelter botState = userDataCacheDogShelter.getUsersCurrentBotState(userId);

        // Assert
        assertNull(botState);
    }

    @Test
    public void testSaveTelegramUser() {
        // Act
        userDataCacheDogShelter.saveTelegramUser(userId, mockTelegramUser);

        // Assert
        assertEquals(mockTelegramUser, userDataCacheDogShelter.getTelegramUser(userId));
    }

    @Test
    public void testGetTelegramUserWhenUserNotInCache() {
        // Arrange
        when(telegramUserService.getById(userId)).thenReturn(mockTelegramUser);

        // Act
        TelegramUser retrievedUser = userDataCacheDogShelter.getTelegramUser(userId);

        // Assert
        assertEquals(mockTelegramUser, retrievedUser);
        verify(telegramUserService, times(1)).getById(userId);
    }

    @Test
    public void testGetTelegramUserWhenUserInCache() {
        // Arrange
        userDataCacheDogShelter.saveTelegramUser(userId, mockTelegramUser);

        // Act
        TelegramUser retrievedUser = userDataCacheDogShelter.getTelegramUser(userId);

        // Assert
        assertEquals(mockTelegramUser, retrievedUser);
        verify(telegramUserService, never()).getById(userId);
    }
}