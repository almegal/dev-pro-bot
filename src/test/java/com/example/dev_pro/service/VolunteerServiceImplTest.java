package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.repository.VolunteerRepository;
import com.example.dev_pro.impl.VolunteerServiceImpl;
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
public class VolunteerServiceImplTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    private Volunteer volunteer;

    @BeforeEach
    public void setUp() {
        volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setNickName("TestNickName");
    }

    @Test
    public void testCreateVolunteer() {
        // Arrange
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Act
        Volunteer createdVolunteer = volunteerService.createVolunteer(volunteer);

        // Assert
        assertNotNull(createdVolunteer);
        assertEquals(volunteer.getId(), createdVolunteer.getId());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    public void testUpdateVolunteer() {
        // Arrange
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Act
        Volunteer updatedVolunteer = volunteerService.updateVolunteer(volunteer);

        // Assert
        assertNotNull(updatedVolunteer);
        assertEquals(volunteer.getId(), updatedVolunteer.getId());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    public void testFindVolunteerById() {
        // Arrange
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));

        // Act
        Volunteer foundVolunteer = volunteerService.findVolunteerById(1L);

        // Assert
        assertNotNull(foundVolunteer);
        assertEquals(volunteer.getId(), foundVolunteer.getId());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindVolunteerById_NotFound() {
        // Arrange
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> volunteerService.findVolunteerById(1L));
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteVolunteerById() {
        // Arrange
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));
        doNothing().when(volunteerRepository).deleteById(any(Long.class));

        // Act
        Volunteer deletedVolunteer = volunteerService.deleteVolunteerById(1L);

        // Assert
        assertNotNull(deletedVolunteer);
        assertEquals(volunteer.getId(), deletedVolunteer.getId());
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllVolunteers() {
        // Arrange
        List<Volunteer> volunteers = Arrays.asList(volunteer);
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        // Act
        Collection<Volunteer> foundVolunteers = volunteerService.findAllVolunteers();

        // Assert
        assertNotNull(foundVolunteers);
        assertEquals(1, foundVolunteers.size());
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    public void testGetListNickNamesOfVolunteers() {
        // Arrange
        List<Volunteer> volunteers = Arrays.asList(volunteer);
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        // Act
        List<String> nickNames = volunteerService.getListNickNamesOfVolunteers();

        // Assert
        assertNotNull(nickNames);
        assertEquals(1, nickNames.size());
        assertEquals(volunteer.getNickName(), nickNames.get(0));
        verify(volunteerRepository, times(1)).findAll();
    }
}