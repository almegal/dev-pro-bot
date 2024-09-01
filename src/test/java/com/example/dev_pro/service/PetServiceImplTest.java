package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.impl.PetServiceImpl;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet pet;

    @BeforeEach
    public void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    public void testCreatePet() {
        // Arrange
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act
        Pet createdPet = petService.createPet(pet);

        // Assert
        assertNotNull(createdPet);
        assertEquals(pet.getId(), createdPet.getId());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    public void testUpdatePet() {
        // Arrange
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act
        Pet updatedPet = petService.updatePet(pet);

        // Assert
        assertNotNull(updatedPet);
        assertEquals(pet.getId(), updatedPet.getId());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    public void testFindPetById() {
        // Arrange
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(pet));

        // Act
        Pet foundPet = petService.findPetById(1L);

        // Assert
        assertNotNull(foundPet);
        assertEquals(pet.getId(), foundPet.getId());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindPetById_NotFound() {
        // Arrange
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> petService.findPetById(1L));
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeletePetById() {
        // Arrange
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).deleteById(any(Long.class));

        // Act
        Pet deletedPet = petService.deletePetById(1L);

        // Assert
        assertNotNull(deletedPet);
        assertEquals(pet.getId(), deletedPet.getId());
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllPets() {
        // Arrange
        List<Pet> pets = Arrays.asList(pet);
        when(petRepository.findAll()).thenReturn(pets);

        // Act
        List<Pet> foundPets = (List<Pet>) petService.findAllPets();

        // Assert
        assertNotNull(foundPets);
        assertEquals(1, foundPets.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByShelterId() {
        // Arrange
        List<Pet> pets = Arrays.asList(pet);
        when(petRepository.findAllByShelterId(any(Integer.class))).thenReturn(pets);

        // Act
        List<Pet> foundPets = petService.findAllByShelterId(1);

        // Assert
        assertNotNull(foundPets);
        assertEquals(1, foundPets.size());
        verify(petRepository, times(1)).findAllByShelterId(1);
    }

    @Test
    public void testFindAllByShelterIdAndIsFreeStatus() {
        // Arrange
        List<Pet> pets = Arrays.asList(pet);
        when(petRepository.findAllByShelterIdAndIsFreeStatus(any(Integer.class), any(Boolean.class))).thenReturn(pets);

        // Act
        List<Pet> foundPets = petService.findAllByShelterIdAndIsFreeStatus(1, true);

        // Assert
        assertNotNull(foundPets);
        assertEquals(1, foundPets.size());
        verify(petRepository, times(1)).findAllByShelterIdAndIsFreeStatus(1, true);
    }

    @Test
    public void testFindAllByAdopterId() {
        // Arrange
        List<Pet> pets = Arrays.asList(pet);
        when(petRepository.findAllByAdopterId(any(Long.class))).thenReturn(pets);

        // Act
        List<Pet> foundPets = petService.findAllByAdopterId(1L);

        // Assert
        assertNotNull(foundPets);
        assertEquals(1, foundPets.size());
        verify(petRepository, times(1)).findAllByAdopterId(1L);
    }
}