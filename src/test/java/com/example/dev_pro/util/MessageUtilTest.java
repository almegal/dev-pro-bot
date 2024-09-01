package com.example.dev_pro.util;

import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.model.TelegramUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageUtilTest {

    @Test
    public void testParseCreateCommand_ValidCommand() {
        // Arrange
        String command = "Иванов Иван Иванович 9052341234 а123bc";
        TelegramUser telegramUser = new TelegramUser();

        // Act
        TelegramUser result = MessageUtil.parseCreateCommand(telegramUser, command);

        // Assert
        assertNotNull(result);
        assertEquals("Иванов", result.getLastName());
        assertEquals("Иван", result.getFirstName());
        assertEquals("Иванович", result.getMiddleName());
        assertEquals("9052341234", result.getPhoneNumber());
        assertEquals("а123", result.getCarNumber());
    }

    @Test
    public void testParseCreateCommand_InvalidCommand() {
        // Arrange
        String command = "Invalid Command";
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }

    @Test
    public void testParseCreateCommand_EmptyCommand() {
        // Arrange
        String command = "";
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }

    @Test
    public void testParseCreateCommand_NullCommand() {
        // Arrange
        String command = null;
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }
}