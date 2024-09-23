package com.example.dev_pro.service;

import com.example.dev_pro.impl.TelegramUserServiceImpl;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.repository.TelegramUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.dev_pro.service.DefaultProps.MOCK_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramUserServiceImplUnitTest {

    @Mock
    private TelegramUserRepository repository;
    @InjectMocks
    private TelegramUserServiceImpl service;

    @Test
    @DisplayName("Возращает по id пользователя")
    public void shouldReturnUserWhenGetById() {
        Long id = 12345L;
        // настройка поведения
        when(repository.findById(id)).thenReturn(Optional.of(MOCK_USER));
        // подготовка актуального результата
        TelegramUser actual = service.getById(id);
        // выполнение теста
        assertEquals(MOCK_USER, actual);
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Возращает нового пользователя если по id пользователь не найден")
    public void shouldReturnEmptyUserObjectIfUserNotFound() {
        // настройка поведения
        when(repository.findById(1L)).thenReturn(Optional.empty());
        // подготовка актуального значения
        TelegramUser actual = service.getById(1L);
        // подготовка ожидаемого результата
        TelegramUser expected = new TelegramUser();
        // тестирование
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Сохраняет пользователя")
    public void shouldSaveUser(){
        //  настройка поведения
        when(repository.save(MOCK_USER)).thenReturn(MOCK_USER);
        // вызов тестируемого метода
        service.save(MOCK_USER);
        // тестируем что выводится сохранение
        verify(repository, times(1)).save(MOCK_USER);
    }
}
