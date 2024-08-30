package com.example.dev_pro.component;

import com.example.dev_pro.component.impl.ChoosingKeyboardButtons;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChoosingKeyboardButtonsTest {

    @InjectMocks
    private ChoosingKeyboardButtons choosingKeyboardButtons;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetKeyboardButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = choosingKeyboardButtons.getKeyboardButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(1, keyboardButtons.size());
        assertEquals(2, keyboardButtons.get(0).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ChoosingKeyboardButtons.CAT_BUTTON, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ChoosingKeyboardButtons.DOG_BUTTON, textField.get(keyboardButtons.get(0).get(1)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }
}