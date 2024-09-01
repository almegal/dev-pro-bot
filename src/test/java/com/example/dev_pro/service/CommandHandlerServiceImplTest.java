package com.example.dev_pro.service;

import com.example.dev_pro.component.impl.ChoosingKeyboardButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.CommandHandlerServiceImpl;
import com.example.dev_pro.impl.shelterImpl.CatShelterServiceImpl;
import com.example.dev_pro.impl.shelterImpl.DogShelterServiceImpl;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommandHandlerServiceImplTest {
    @Mock
    private TelegramBot telegramBot;

    @Spy
    private TelegramBotConfiguration tBotConfig;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private TelegramUserService telegramUserService;

    @Mock
    private CatShelterServiceImpl catShelterService;

    @Mock
    private DogShelterServiceImpl dogShelterService;
    @Mock
    private ChoosingKeyboardButtons choosingKeyboardButtons;

    @InjectMocks
    private CommandHandlerServiceImpl commandHandlerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCommand_WithCallVolunteer() {
        String command = "/call";
        String expectedMessage = "Call Volunteer";
        when(tBotConfig.getCallVolunteerMsg()).thenReturn(expectedMessage);

        String result = commandHandlerService.handleCommand(command);

        assertEquals(expectedMessage, result);
        verify(tBotConfig, times(1)).getCallVolunteerMsg();
    }

    @Test
    void testHandleCommand_WithUnknownCommand() {
        String command = "/unknown";
        String expectedMessage = "Error";
        when(tBotConfig.getErrorMsg()).thenReturn(expectedMessage);

        String result = commandHandlerService.handleCommand(command);

        assertEquals(expectedMessage, result);
        verify(tBotConfig, times(1)).getErrorMsg();
    }

    @Test
    void testCommandProcessing_WithCallVolunteer() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(message.text()).thenReturn("/call");
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(456L);
        when(message.chat().username()).thenReturn("user");

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setTelegramId(456L);
        telegramUser.setShelter("Cat");
        when(telegramUserService.getById(456L)).thenReturn(telegramUser);

        String expectedMessage = "Call Volunteer";
        when(tBotConfig.getCallVolunteerMsg()).thenReturn(expectedMessage);
        when(volunteerService.getListNickNamesOfVolunteers()).thenReturn(Collections.singletonList("@volunteer"));
        List<Volunteer> volunteers = Collections.singletonList(new Volunteer());
        when(volunteerService.findAllVolunteers()).thenReturn(volunteers);

        commandHandlerService.commandProcessing(update);

        verify(telegramUserService, times(1)).getById(456L);
        verify(volunteerService, times(1)).getListNickNamesOfVolunteers();
        verify(volunteerService, times(1)).findAllVolunteers();
        verify(telegramBot, times(2)).execute(any(SendMessage.class));
    }

    @Test
    void testSendMsg() {
        Long chatId = 123L;
        String message = "Hello, world!";

        commandHandlerService.sendMsg(chatId, message);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(captor.capture());

        SendMessage capturedMessage = captor.getValue();

    }

    @Test
    void testCommandProcessing_NewUser() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        User user = mock(User.class);
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(456L);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(message.text()).thenReturn("/start");

        TelegramUser telegramUser = new TelegramUser();  // Новый пользователь
        when(telegramUserService.getById(456L)).thenReturn(telegramUser);

        when(tBotConfig.getStartMsg()).thenReturn("Welcome!");
//        when(choosingKeyboardButtons.getKeyboardButtons()).thenReturn(null);

        commandHandlerService.commandProcessing(update);

        verify(telegramUserService, times(1)).getById(456L);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
}
