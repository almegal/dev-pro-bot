package com.example.dev_pro.service;

import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.example.dev_pro.impl.AvatarPetServiceImpl;
import com.example.dev_pro.model.AvatarPet;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.AvatarPetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvatarPetServiceImplTest {

    @Mock
    private AvatarPetRepository avatarPetRepository;

    @Mock
    private PetService petService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private AvatarPetServiceImpl avatarPetService;

    private AvatarPet testAvatarPet;
    private Pet pet;

    @BeforeEach
    public void setUp() {
        // Инициализация объекта Pet
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Test Pet");
        pet.setPetType(PetType.CAT);
        pet.setSex(Sex.MAN);
        pet.setAge(2);
        pet.setIsFreeStatus(true);

        // Инициализация объекта AvatarPet
        testAvatarPet = new AvatarPet();
        testAvatarPet.setId(1L);
        testAvatarPet.setFilePath("path/to/avatar.jpg");
        testAvatarPet.setFileSize(1024L);
        testAvatarPet.setMediaType("image/jpeg");
        testAvatarPet.setData(new byte[]{1, 2, 3});
        testAvatarPet.setPet(pet);
    }

    @Test
    @DisplayName("Find all avatars")
    public void testFindAllAvatars() {
        // Создаем коллекцию с одним элементом
        List<AvatarPet> avatarPets = new ArrayList<>();
        avatarPets.add(testAvatarPet);

        // Возвращаем коллекцию с одним элементом
        when(avatarPetRepository.findAll()).thenReturn(avatarPets);

        // Вызываем метод сервиса
        Collection<AvatarPet> avatars = avatarPetService.findAllAvatars();

        // Проверяем результаты
        assertNotNull(avatars, "The returned collection should not be null");
        assertEquals(1, avatars.size(), "The size of the returned collection should be 1");
        verify(avatarPetRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find all avatars when none exist")
    public void testFindAllAvatarsWhenNoneExist() {
        when(avatarPetRepository.findAll()).thenReturn(Collections.emptyList());

        Collection<AvatarPet> avatars = avatarPetService.findAllAvatars();

        assertNotNull(avatars, "The returned collection should not be null");
        assertTrue(avatars.isEmpty(), "The returned collection should be empty");
        verify(avatarPetRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find avatar by pet ID")
    public void testFindAvatarByPetId() {
        when(avatarPetRepository.findAvatarByPetId(any(Long.class))).thenReturn(Optional.of(testAvatarPet));

        AvatarPet foundAvatar = avatarPetService.findAvatarByPetId(1L);

        assertNotNull(foundAvatar);
        verify(avatarPetRepository, times(1)).findAvatarByPetId(1L);
    }

    @Test
    @DisplayName("Find avatar by pet ID when none exist")
    public void testFindAvatarByPetIdWhenNoneExist() {
        when(avatarPetRepository.findAvatarByPetId(any(Long.class))).thenReturn(Optional.empty());

        AvatarPet foundAvatar = avatarPetService.findAvatarByPetId(1L);

        assertNotNull(foundAvatar, "The returned avatar should not be null");
        assertNull(foundAvatar.getId(), "The returned avatar should have a null ID");
        verify(avatarPetRepository, times(1)).findAvatarByPetId(1L);
    }

    @Test
    @DisplayName("Upload avatar")
    public void testUploadAvatar() throws IOException {
        // Arrange
        when(petService.findPetById(any(Long.class))).thenReturn(pet);
        when(multipartFile.getOriginalFilename()).thenReturn("avatar.jpg");

        // Создаем реальный InputStream с тестовыми данными изображения
        InputStream testImageStream = new ByteArrayInputStream(createTestImage());
        when(multipartFile.getInputStream()).thenReturn(testImageStream);
        when(multipartFile.getSize()).thenReturn(1024L);
        when(multipartFile.getContentType()).thenReturn("image/jpeg");

        Path tempDir = Files.createTempDirectory("avatars");
        avatarPetService.setAvatarsDir(tempDir.toString());

        // Act
        avatarPetService.uploadAvatar(1L, multipartFile);

        // Assert
        verify(petService, times(1)).findPetById(1L);
        verify(avatarPetRepository, times(1)).save(any(AvatarPet.class));

        // Дополнительные проверки
        Path expectedFilePath = tempDir.resolve("1.jpg");
        assertTrue(Files.exists(expectedFilePath), "The file should exist at the expected location");
        assertEquals(825L, Files.size(expectedFilePath), "The file size should match the uploaded file size");
    }

    private byte[] createTestImage() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 100, 100);
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        return baos.toByteArray();
    }
}