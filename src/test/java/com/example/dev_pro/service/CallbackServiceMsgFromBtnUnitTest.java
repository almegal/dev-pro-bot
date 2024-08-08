package com.example.dev_pro.service;

import com.example.dev_pro.component.Buttons;
import com.example.dev_pro.impl.CallbackServiceMsgFromBtn;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;
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
    private TelegramUserService service;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private User user;
    @Mock
    private Chat chat;

    @InjectMocks
    private CallbackServiceMsgFromBtn callback;

    public static Stream<Arguments> argsProvider() {
        return Stream.of(
                Arguments.of("Cat", (Supplier<Buttons>) () -> (Buttons) catShelterService),
                Arguments.of("Dog", (Supplier<Buttons>) () -> (Buttons) dogShelterService)
        );
    }

    @BeforeEach
    public void init() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(message.chat()).thenReturn(chat);
        when(user.id()).thenReturn(MOCK_USER.getTelegramId());
    }

    @ParameterizedTest
    @DisplayName("Проверяет корректное извлечения методов")
    @MethodSource("argsProvider")
    public void ShouldInvokeCorrectServiceImplementation(String shelter, Supplier<Buttons> shelterGetter) {
        // Устанавливаем значение
        MOCK_USER.setShelter(shelter);
        // Настройка поведения
        when(message.text()).thenReturn(shelter);
        doNothing().when(service).save(MOCK_USER);
        // Вызов тестируемого метода
        callback.handleCallback(update);
        // Получаем объект из метода
        Buttons shelterService = shelterGetter.get();
        // Проверка
        verify(bot, times(1)).execute(any(SendMessage.class));
        verify(shelterService, times(1)).getKeyboardButtons();
        verify(service, times(1)).save(MOCK_USER);
    }

}
