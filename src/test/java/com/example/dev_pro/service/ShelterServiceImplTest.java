package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.impl.ShelterServiceImpl;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import com.example.dev_pro.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceImplTest {

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ShelterServiceImpl shelterService;

    private Shelter shelter;

    @BeforeEach
    public void setUp() {
        shelter = new Shelter();
        shelter.setId(1);
        shelter.setName("Test Shelter");
    }

    @Test
    public void testCreateShelter() {
        // Arrange
        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        // Act
        Shelter createdShelter = shelterService.createShelter(shelter);

        // Assert
        assertNotNull(createdShelter);
        assertEquals(shelter.getId(), createdShelter.getId());
        verify(shelterRepository, times(1)).save(shelter);
    }

    @Test
    public void testUpdateShelter() {
        // Arrange
        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        // Act
        Shelter updatedShelter = shelterService.updateShelter(shelter);

        // Assert
        assertNotNull(updatedShelter);
        assertEquals(shelter.getId(), updatedShelter.getId());
        verify(shelterRepository, times(1)).save(shelter);
    }

    @Test
    public void testFindShelterById() {
        // Arrange
        when(shelterRepository.findById(any(Integer.class))).thenReturn(Optional.of(shelter));

        // Act
        Shelter foundShelter = shelterService.findShelterById(1);

        // Assert
        assertNotNull(foundShelter);
        assertEquals(shelter.getId(), foundShelter.getId());
        verify(shelterRepository, times(1)).findById(1);
    }

    @Test
    public void testFindShelterById_NotFound() {
        // Arrange
        when(shelterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> shelterService.findShelterById(1));
        verify(shelterRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteShelterById() {
        // Arrange
        when(shelterRepository.findById(any(Integer.class))).thenReturn(Optional.of(shelter));
        doNothing().when(shelterRepository).deleteById(any(Integer.class));

        // Act
        Shelter deletedShelter = shelterService.deleteShelterById(1);

        // Assert
        assertNotNull(deletedShelter);
        assertEquals(shelter.getId(), deletedShelter.getId());
        verify(shelterRepository, times(1)).findById(1);
        verify(shelterRepository, times(1)).deleteById(1);
    }

    @Test
    public void testFindAllShelters() {
        // Arrange
        List<Shelter> shelters = Arrays.asList(shelter);
        when(shelterRepository.findAll()).thenReturn(shelters);

        // Act
        Collection<Shelter> foundShelters = shelterService.findAllShelters();

        // Assert
        assertNotNull(foundShelters);
        assertEquals(1, foundShelters.size());
        verify(shelterRepository, times(1)).findAll();
    }

    @Test
    public void testFindPetsByShelterId() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        List<Pet> pets = Arrays.asList(pet);
        shelter.setPets(pets);
        when(shelterRepository.findById(any(Integer.class))).thenReturn(Optional.of(shelter));

        // Act
        Collection<Pet> foundPets = shelterService.findPetsByShelterId(1);

        // Assert
        assertNotNull(foundPets);
        assertEquals(1, foundPets.size());
        verify(shelterRepository, times(1)).findById(1);
    }

    @Test
    public void testFindPetsByShelterId_NotFound() {
        // Arrange
        when(shelterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> shelterService.findPetsByShelterId(1));
        verify(shelterRepository, times(1)).findById(1);
    }
}