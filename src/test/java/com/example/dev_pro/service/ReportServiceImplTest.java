package com.example.dev_pro.service;

import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.impl.ReportServiceImpl;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Report report1;
    private Report report2;
    private Report report3;
    private Report report4;


    @BeforeEach
    public void setUp() {

        report1  = new Report(1, LocalDate.of(2024, Month.AUGUST, 28), "Report text1",
                "Report file path1", 1, "Report media type1", true);
        report2  = new Report(2, LocalDate.of(2024, Month.AUGUST, 28), "Report text2",
                "Report file path2", 2, "Report media type2", true);
        report3  = new Report(3, LocalDate.of(2024, Month.AUGUST, 29), "Report text3",
                "Report file path3", 3, "Report media type3", null);
        report4  = new Report(4, LocalDate.of(2024, Month.AUGUST, 29), "Report text4",
                "Report file path4", 4, "Report media type4", null);

        report1.setPet(new Pet(1L, PetType.CAT, "Барсик", Sex.MAN, 1, false));
        report1.setAdopter(new Adopter(1L, false));

        report2.setPet(new Pet(2L, PetType.CAT, "Мурка", Sex.WOMAN, 2, false));
        report2.setAdopter(new Adopter(2L, false));

        report3.setPet(new Pet(3L, PetType.DOG, "Альта", Sex.WOMAN, 3, false));
        report3.setAdopter(new Adopter(1L, false));

        report4.setPet(new Pet(4L, PetType.DOG, "Алый", Sex.MAN, 4, false));
        report4.setAdopter(new Adopter(2L, false));
    }

    @Test
    public void testCreateReport() {
        // Arrange
        when(reportRepository.save(any(Report.class))).thenReturn(report1);

        // Act
        Report createdReport = reportService.createReport(report1);

        // Assert
        assertNotNull(createdReport);
        assertEquals(report1.getId(), createdReport.getId());
        assertEquals(report1.getDateReport(), createdReport.getDateReport());
        assertEquals(report1.getTextReport(), createdReport.getTextReport());
        assertEquals(report1.getFilePath(), createdReport.getFilePath());
        assertEquals(report1.getFileSize(), createdReport.getFileSize());
        assertEquals(report1.getMediaType(), createdReport.getMediaType());
        assertEquals(report1.getIsViewed(), createdReport.getIsViewed());
        verify(reportRepository, times(1)).save(report1);
    }

    @Test
    public void testUpdateReport() {
        // Arrange
        when(reportRepository.save(any(Report.class))).thenReturn(report2);

        // Act
        Report updatedReport = reportService.updateReport(report2);

        // Assert
        assertNotNull(updatedReport);
        assertEquals(report2.getId(), updatedReport.getId());
        assertEquals(report2.getDateReport(), updatedReport.getDateReport());
        assertEquals(report2.getTextReport(), updatedReport.getTextReport());
        assertEquals(report2.getFilePath(), updatedReport.getFilePath());
        assertEquals(report2.getFileSize(), updatedReport.getFileSize());
        assertEquals(report2.getMediaType(), updatedReport.getMediaType());
        assertEquals(report2.getIsViewed(), updatedReport.getIsViewed());
        verify(reportRepository, times(1)).save(report2);
    }

    @Test
    public void testFindReportById() {
        // Arrange
        when(reportRepository.findById(any(Integer.class))).thenReturn(Optional.of(report3));

        // Act
        Report foundReport = reportService.findReportById(report3.getId());

        // Assert
        assertNotNull(foundReport);
        assertEquals(report3, foundReport);
        verify(reportRepository, times(1)).findById(3);
    }

    @Test
    public void testFindReportById_NotFound() {
        // Arrange
        when(reportRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> reportService.findReportById(5));
        verify(reportRepository, times(1)).findById(5);
    }

    @Test
    public void testDeleteReportById() {
        // Arrange
        when(reportRepository.findById(any(Integer.class))).thenReturn(Optional.of(report1));
        doNothing().when(reportRepository).deleteById(any(Integer.class));

        // Act
        Report deletedReport = reportService.deleteReportById(1);

        // Assert
        assertNotNull(deletedReport);
        assertEquals(report1, deletedReport);
        verify(reportRepository, times(1)).findById(1);
        verify(reportRepository, times(1)).deleteById(1);
    }

    @Test
    public void testFindReportByPetIdAndDateReport() {
        // Arrange
        when(reportRepository.findReportByPetIdAndDateReport(any(Long.class), any(LocalDate.class)))
                .thenReturn(Optional.of(report3));

        // Act
        Report foundReport = reportService.findReportByPetIdAndDateReport(3L,
                LocalDate.of(2024, Month.AUGUST, 29));

        // Assert
        assertNotNull(foundReport);
        assertEquals(report3, foundReport);
        verify(reportRepository, times(1))
                .findReportByPetIdAndDateReport(3L, LocalDate.of(2024, Month.AUGUST, 29));
    }

    @Test
    public void testFindReportByPetIdAndDateReport_NotFound() {
        // Arrange
        when(reportRepository.findReportByPetIdAndDateReport(any(Long.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> reportService.findReportByPetIdAndDateReport(5L,
                LocalDate.of(2024, Month.AUGUST, 27)));
        verify(reportRepository, times(1)).findReportByPetIdAndDateReport(5L,
                LocalDate.of(2024, Month.AUGUST, 27));
    }

    @Test
    public void testFindAllByIsViewedNull() {
        // Arrange
        List<Report> expected = Arrays.asList(report3, report4);
        when(reportRepository.findAllByIsViewedNull()).thenReturn(expected);

        // Act
//        reportService.createReport(report1);
//        reportService.createReport(report2);
//        reportService.createReport(report3);
//        reportService.createReport(report4);
        List<Report> foundReports = reportService.findAllByIsViewedNull();

        // Assert
        assertNotNull(foundReports);
        assertEquals(expected.size(), foundReports.size());
        assertEquals(expected, foundReports);
        verify(reportRepository, times(1)).findAllByIsViewedNull();
    }

    @Test
    public void testFindAllByAdopterId() {
        // Arrange
        List<Report> expected = Arrays.asList(report1, report3);
        when(reportRepository.findAllByAdopterId(any(Long.class))).thenReturn(expected);

        // Act
        List<Report> foundReports = reportService.findAllByAdopterId(1L);

        // Assert
        assertNotNull(foundReports);
        assertEquals(expected.size(), foundReports.size());
        assertEquals(expected, foundReports);
        verify(reportRepository, times(1)).findAllByAdopterId(1L);
    }

    @Test
    public void testFindAllByAdopterIdAndPetId() {
        // Arrange
        List<Report> expected = Collections.singletonList(report4);
        when(reportRepository.findAllByAdopterIdAndPetId(any(Long.class), any(Long.class))).thenReturn(expected);

        // Act
        List<Report> foundReports = reportService.findAllByAdopterIdAndPetId(2L, 4L);

        // Assert
        assertNotNull(foundReports);
        assertEquals(expected.size(), foundReports.size());
        assertEquals(expected, foundReports);
        verify(reportRepository, times(1)).findAllByAdopterIdAndPetId(2L, 4L);
    }

    @Test
    public void testDeleteAllByAdopterId() {
        // Arrange
        doNothing().when(reportRepository).deleteAllByAdopterId(any(Long.class));

        // Act
        reportService.deleteAllByAdopterId(2L);

        // Assert

        verify(reportRepository, times(1)).deleteAllByAdopterId(2L);

    }






}
