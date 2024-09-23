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
        assertEquals("а123bc", result.getCarNumber());
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

    @Test
    public void testParseCreateCommandWhenCommandIsValidThenReturnTelegramUser() {
        // Test parseCreateCommand with a valid command
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
        assertEquals("а123bc", result.getCarNumber());
    }

    @Test
    public void testParseCreateCommandWhenCommandIsInvalidThenThrowInvalidInputException() {
        // Test parseCreateCommand with an invalid command
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
    public void testParseCreateCommandWhenCommandIsEmptyThenThrowInvalidInputException() {
        // Test parseCreateCommand with an empty command
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
    public void testParseCreateCommandWhenCommandIsNullThenThrowInvalidInputException() {
        // Test parseCreateCommand with a null command
        // Arrange
        String command = null;
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }

    /**
     * Test parseCreateCommand with a valid command.
     * This test case verifies the behavior of the method when a valid command is passed.
     * It sets up a valid command and a TelegramUser object, calls the parseCreateCommand method,
     * and then checks that the returned TelegramUser object has the expected values.
     */
    @Test
    public void testParseCreateCommandWhenValidCommandThenReturnTelegramUser() {
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
        assertEquals("а123bc", result.getCarNumber());
    }

    /**
     * Test parseCreateCommand with an invalid command.
     * This test case verifies the behavior of the method when an invalid command is passed.
     * It sets up an invalid command and a TelegramUser object, calls the parseCreateCommand method,
     * and then checks that an InvalidInputException is thrown with the expected message.
     */
    @Test
    public void testParseCreateCommandWhenInvalidCommandThenThrowInvalidInputException() {
        // Arrange
        String command = "Invalid Command";
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }

    /**
     * Test parseCreateCommand with an empty command.
     * This test case verifies the behavior of the method when an empty command is passed.
     * It sets up an empty command and a TelegramUser object, calls the parseCreateCommand method,
     * and then checks that an InvalidInputException is thrown with the expected message.
     */
    @Test
    public void testParseCreateCommandWhenEmptyCommandThenThrowInvalidInputException() {
        // Arrange
        String command = "";
        TelegramUser telegramUser = new TelegramUser();

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            MessageUtil.parseCreateCommand(telegramUser, command);
        });

        assertEquals("Incorrect command: " + command, exception.getMessage());
    }

    /**
     * Test parseCreateCommand with a null command.
     * This test case verifies the behavior of the method when a null command is passed.
     * It sets up a null command and a TelegramUser object, calls the parseCreateCommand method,
     * and then checks that an InvalidInputException is thrown with the expected message.
     */
    @Test
    public void testParseCreateCommandWhenNullCommandThenThrowInvalidInputException() {
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