package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.impl.AdopterServiceImpl;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.repository.AdopterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdopterServiceImplTest {

    @Mock
    private AdopterRepository repository;

    @InjectMocks
    private AdopterServiceImpl service;

    private Adopter adopter;

    @BeforeEach
    public void setUp() {
        adopter = new Adopter();
        adopter.setId(1L);
    }

    @Test
    public void testFindAdopterById_Success() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(adopter));

        // Act
        Adopter foundAdopter = service.findAdopterById(1L);

        // Assert
        assertEquals(adopter, foundAdopter);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testFindAdopterById_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.findAdopterById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testFindAdopterByTelegramUserId_Success() {
        // Arrange
        when(repository.findAdopterByTelegramUserId(12345L)).thenReturn(Optional.of(adopter));

        // Act
        Adopter foundAdopter = service.findAdopterByTelegramUserId(12345L);

        // Assert
        assertEquals(adopter, foundAdopter);
        verify(repository, times(1)).findAdopterByTelegramUserId(12345L);
    }

    @Test
    public void testFindAdopterByTelegramUserId_NotFound() {
        // Arrange
        when(repository.findAdopterByTelegramUserId(12345L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.findAdopterByTelegramUserId(12345L));
        verify(repository, times(1)).findAdopterByTelegramUserId(12345L);
    }
}