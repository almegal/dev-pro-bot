package com.example.dev_pro.cache;

import com.example.dev_pro.cache.impl.ReportDataCacheImpl;
import com.example.dev_pro.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReportDataCacheImplTest {

    @InjectMocks
    private ReportDataCacheImpl reportDataCache;

    private Report mockReport;
    private final Long adopterId = 1L;

    @BeforeEach
    public void setUp() {
        mockReport = new Report();
        mockReport.setId(1);
        mockReport.setDateReport(LocalDate.now());
        mockReport.setTextReport("Sample report text");
        mockReport.setFilePath("/path/to/file");
        mockReport.setFileSize(1024L);
        mockReport.setMediaType("text/plain");
        mockReport.setIsViewed(false);
    }

    @Test
    public void testGetReportWhenReportPresentThenReturnReport() {
        // Arrange
        reportDataCache.saveReport(adopterId, mockReport);

        // Act
        Report retrievedReport = reportDataCache.getReport(adopterId);

        // Assert
        assertThat(retrievedReport).isNotNull();
        assertThat(retrievedReport).isEqualTo(mockReport);
    }

    @Test
    public void testGetReportWhenReportNotPresentThenReturnNewReport() {
        // Act
        Report retrievedReport = reportDataCache.getReport(adopterId);

        // Assert
        assertThat(retrievedReport).isNotNull();
        assertThat(retrievedReport.getId()).isNull();
        assertThat(retrievedReport.getDateReport()).isNull();
        assertThat(retrievedReport.getTextReport()).isNull();
        assertThat(retrievedReport.getFilePath()).isNull();
        assertThat(retrievedReport.getFileSize()).isEqualTo(0L);
        assertThat(retrievedReport.getMediaType()).isNull();
        assertThat(retrievedReport.getIsViewed()).isNull();
    }

    @Test
    public void testSaveReportThenReportAddedToCache() {
        // Act
        reportDataCache.saveReport(adopterId, mockReport);

        // Assert
        Report retrievedReport = reportDataCache.getReport(adopterId);
        assertThat(retrievedReport).isNotNull();
        assertThat(retrievedReport).isEqualTo(mockReport);
    }
}