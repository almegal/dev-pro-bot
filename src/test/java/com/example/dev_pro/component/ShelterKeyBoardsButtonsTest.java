package com.example.dev_pro.component;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
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

public class ShelterKeyBoardsButtonsTest {

    @InjectMocks
    private ShelterKeyBoardsButtons shelterKeyBoardsButtons;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetKeyboardButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = shelterKeyBoardsButtons.getKeyboardButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(1, keyboardButtons.size());
        assertEquals(4, keyboardButtons.get(0).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ShelterKeyBoardsButtons.INFO_COM, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ShelterKeyBoardsButtons.TAKE_COM, textField.get(keyboardButtons.get(0).get(1)));
        assertEquals(ShelterKeyBoardsButtons.REPORT_COM, textField.get(keyboardButtons.get(0).get(2)));
        assertEquals(ShelterKeyBoardsButtons.CALL_COM, textField.get(keyboardButtons.get(0).get(3)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }

    @Test
    public void testGetInfoKeyboardButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = shelterKeyBoardsButtons.getInfoKeyboardButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(2, keyboardButtons.size());
        assertEquals(3, keyboardButtons.get(0).size());
        assertEquals(3, keyboardButtons.get(1).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ShelterKeyBoardsButtons.OVERVIEW_COM, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ShelterKeyBoardsButtons.ADDRESS_COM, textField.get(keyboardButtons.get(0).get(1)));
        assertEquals(ShelterKeyBoardsButtons.CAR_PASS_COM, textField.get(keyboardButtons.get(0).get(2)));
        assertEquals(ShelterKeyBoardsButtons.SAFETY_RULES_COM, textField.get(keyboardButtons.get(1).get(0)));
        assertEquals(ShelterKeyBoardsButtons.USER_CONTACT_COM, textField.get(keyboardButtons.get(1).get(1)));
        assertEquals(ShelterKeyBoardsButtons.COME_BACK_COM, textField.get(keyboardButtons.get(1).get(2)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }

    @Test
    public void testGetTakeKeyboardButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = shelterKeyBoardsButtons.getTakeKeyboardButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(3, keyboardButtons.size());
        assertEquals(1, keyboardButtons.get(0).size());
        assertEquals(3, keyboardButtons.get(1).size());
        assertEquals(3, keyboardButtons.get(2).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ShelterKeyBoardsButtons.LIST_ANIMALS_COM, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ShelterKeyBoardsButtons.MEETING_ANIMALS_COM, textField.get(keyboardButtons.get(1).get(0)));
        assertEquals(ShelterKeyBoardsButtons.LIST_DOCUMENTS_COM, textField.get(keyboardButtons.get(1).get(1)));
        assertEquals(ShelterKeyBoardsButtons.RECOMMENDATIONS_COM, textField.get(keyboardButtons.get(1).get(2)));
        assertEquals(ShelterKeyBoardsButtons.REASONS_REFUSAL_COM, textField.get(keyboardButtons.get(2).get(0)));
        assertEquals(ShelterKeyBoardsButtons.USER_CONTACT_COM, textField.get(keyboardButtons.get(2).get(1)));
        assertEquals(ShelterKeyBoardsButtons.COME_BACK_COM, textField.get(keyboardButtons.get(2).get(2)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }

    @Test
    public void testGetRecommendationsButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = shelterKeyBoardsButtons.getRecommendationsButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(3, keyboardButtons.size());
        assertEquals(1, keyboardButtons.get(0).size());
        assertEquals(3, keyboardButtons.get(1).size());
        assertEquals(3, keyboardButtons.get(2).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ShelterKeyBoardsButtons.RECOMM_FOR_TRANSPORTING_THE_ANIMAL, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ShelterKeyBoardsButtons.TO_SET_UP_HOME_FOR_PUPPY, textField.get(keyboardButtons.get(1).get(0)));
        assertEquals(ShelterKeyBoardsButtons.SETTING_UP_HOME_FOR_AN_ADULT_PET, textField.get(keyboardButtons.get(1).get(1)));
        assertEquals(ShelterKeyBoardsButtons.PROVIDING_HOME_FOR_ANIMAL_DISABILITY, textField.get(keyboardButtons.get(1).get(2)));
        assertEquals(ShelterKeyBoardsButtons.ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL, textField.get(keyboardButtons.get(2).get(0)));
        assertEquals(ShelterKeyBoardsButtons.CONTACT_DETAILS_HANDLER, textField.get(keyboardButtons.get(2).get(1)));
        assertEquals(ShelterKeyBoardsButtons.RECOMM_COME_BACK_COM, textField.get(keyboardButtons.get(2).get(2)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }

    @Test
    public void testGetReportButtons() throws NoSuchFieldException, IllegalAccessException {
        // Act
        Keyboard keyboard = shelterKeyBoardsButtons.getReportButtons();

        // Assert
        assertTrue(keyboard instanceof ReplyKeyboardMarkup);
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) keyboard;

        // Use reflection to access private fields
        Field keyboardField = ReplyKeyboardMarkup.class.getDeclaredField("keyboard");
        keyboardField.setAccessible(true);
        List<List<KeyboardButton>> keyboardButtons = (List<List<KeyboardButton>>) keyboardField.get(replyKeyboardMarkup);

        assertEquals(2, keyboardButtons.size());
        assertEquals(2, keyboardButtons.get(0).size());
        assertEquals(1, keyboardButtons.get(1).size());

        Field textField = KeyboardButton.class.getDeclaredField("text");
        textField.setAccessible(true);

        assertEquals(ShelterKeyBoardsButtons.REPORT_FORMAT, textField.get(keyboardButtons.get(0).get(0)));
        assertEquals(ShelterKeyBoardsButtons.SEND_PHOTO_REPORT, textField.get(keyboardButtons.get(0).get(1)));
        assertEquals(ShelterKeyBoardsButtons.REPORT_COME_BACK_COM, textField.get(keyboardButtons.get(1).get(0)));

        // Check if resizeKeyboard is set to true
        Field resizeKeyboardField = ReplyKeyboardMarkup.class.getDeclaredField("resize_keyboard");
        resizeKeyboardField.setAccessible(true);
        boolean resizeKeyboard = (boolean) resizeKeyboardField.get(replyKeyboardMarkup);
        assertTrue(resizeKeyboard);
    }
}