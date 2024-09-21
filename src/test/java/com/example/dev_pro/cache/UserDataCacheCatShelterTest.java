package com.example.dev_pro.cache;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
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

public class UserDataCacheCatShelterTest {

    @Mock
    private TelegramUserService telegramUserService;

    @InjectMocks
    private UserDataCacheCatShelter userDataCacheCatShelter;

    private final Long userId = 1L;
    private TelegramUser mockTelegramUser;
    private BotStateCatShelter mockBotState;

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

        mockBotState = BotStateCatShelter.FILLING_PROFILE;
    }

    @Test
    public void testSetUsersCurrentBotState() {
        // Act
        userDataCacheCatShelter.setUsersCurrentBotState(userId, mockBotState);

        // Assert
        assertEquals(mockBotState, userDataCacheCatShelter.getUsersCurrentBotState(userId));
    }

    @Test
    public void testGetUsersCurrentBotStateWhenStateNotSet() {
        // Act
        BotStateCatShelter botState = userDataCacheCatShelter.getUsersCurrentBotState(userId);

        // Assert
        assertNull(botState);
    }

    @Test
    public void testSaveTelegramUser() {
        // Act
        userDataCacheCatShelter.saveTelegramUser(userId, mockTelegramUser);

        // Assert
        assertEquals(mockTelegramUser, userDataCacheCatShelter.getTelegramUser(userId));
    }

    @Test
    public void testGetTelegramUserWhenUserNotInCache() {
        // Arrange
        when(telegramUserService.getTelegramById(userId)).thenReturn(mockTelegramUser);

        // Act
        TelegramUser retrievedUser = userDataCacheCatShelter.getTelegramUser(userId);

        // Assert
        assertEquals(mockTelegramUser, retrievedUser);
        verify(telegramUserService, times(1)).getTelegramById(userId);
    }

    @Test
    public void testGetTelegramUserWhenUserInCache() {
        // Arrange
        userDataCacheCatShelter.saveTelegramUser(userId, mockTelegramUser);

        // Act
        TelegramUser retrievedUser = userDataCacheCatShelter.getTelegramUser(userId);

        // Assert
        assertEquals(mockTelegramUser, retrievedUser);
        verify(telegramUserService, never()).getById(userId);
    }
}