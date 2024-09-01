package com.example.dev_pro.service;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.impl.CallbackServiceMsgFromBtn;
import com.example.dev_pro.impl.TelegramUserServiceImpl;
import com.example.dev_pro.impl.shelterImpl.CatShelterServiceImpl;
import com.example.dev_pro.impl.shelterImpl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static com.example.dev_pro.service.DefaultProps.MOCK_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CallbackServiceMsgFromBtnUnitTest {

    @Mock
    private static CatShelterServiceImpl catShelterService;
    @Mock
    private static DogShelterServiceImpl dogShelterService;
    @Mock
    private TelegramBot bot;
    @Mock
    private TelegramUserServiceImpl service;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private User user;
    @Mock
    private Chat chat;
    @Spy
    private ShelterKeyBoardsButtons boardsButtons;

    @InjectMocks
    private CallbackServiceMsgFromBtn callback;

    public static Stream<Arguments> argsProvider() {
        return Stream.of(
                Arguments.of("Cat"),
                Arguments.of("Dog")
        );
    }

    @BeforeEach
    public void init() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(0L);
        when(user.id()).thenReturn(MOCK_USER.getTelegramId());
    }

    @ParameterizedTest
    @DisplayName("Проверяет корректное извлечения методов")
    @MethodSource("argsProvider")
    public void ShouldInvokeCorrectServiceImplementation(String shelter) {
        // Устанавливаем значение
        MOCK_USER.setShelter(shelter);
        // Настройка поведения
//        when(boardsButtons.getKeyboardButtons()).thenReturn(any());
        when(message.text()).thenReturn(shelter);
//        when(bot.execute(any(SendMessage.class))).thenReturn(any(SendResponse.class));
        doNothing().when(service).save(MOCK_USER);

        // Вызов тестируемого метода
        callback.handleCallback(update);

        // Проверка
        verify(bot, times(1)).execute(any(SendMessage.class));
        verify(boardsButtons, times(1)).getKeyboardButtons();
        verify(service, times(1)).save(MOCK_USER);
    }

}
