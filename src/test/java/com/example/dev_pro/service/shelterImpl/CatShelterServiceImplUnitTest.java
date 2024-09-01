package com.example.dev_pro.service.shelterImpl;

import com.example.dev_pro.botapi.BotStateContextCatShelter;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
import com.example.dev_pro.impl.shelterImpl.CatShelterServiceImpl;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatShelterServiceImplUnitTest extends ShelterServiceImplTest {
    @Mock
    private UserDataCacheCatShelter catShelter;
    @Mock
    private BotStateContextCatShelter contextCatShelter;
    @InjectMocks
    private CatShelterServiceImpl service;

    @Override
    @BeforeEach
    public void init() {
        super.service = service;
    }
    @Test
    @DisplayName("Отправка сообщения пользователю при корректных значениях")
    public void botShouldExecuteWithCorrectMessage() {
        // настройка поведения моков
        when(UPDATE_MOCK.message()).thenReturn(MESSAGE_MOCK);
        when(MESSAGE_MOCK.chat()).thenReturn(CHAT_MOCK);
        when(MESSAGE_MOCK.from()).thenReturn(USER_MOCK);
        when(USER_MOCK.id()).thenReturn(1L);
        when(CHAT_MOCK.id()).thenReturn(1L);
        when(MESSAGE_MOCK.text()).thenReturn("anyString()");

        // Вызов тестируемого метода
        service.handleUpdate(UPDATE_MOCK);

        // проверяем что идет вызов отправки сообщений
        verify(contextCatShelter, times(1)).processInputMessage(any(), any(Message.class));
    }
    @Test
    @DisplayName("Не должен вызываться метод отпрвки сообщения при иключнии")
    public void shouldReturnExceptionAndNeverSendMessage() {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            service.handleUpdate(null);
        });

        verify(contextCatShelter, times(0)).processInputMessage(any(), any());
    }

}
