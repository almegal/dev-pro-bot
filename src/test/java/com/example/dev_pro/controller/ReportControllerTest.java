package com.example.dev_pro.controller;


import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.ReportService;
import com.example.dev_pro.service.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static com.example.dev_pro.controller.ReportTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private PetService petService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldCreateReport() throws Exception {

        createMockPet();
        createMockUser();
        createMockAdopter();

        ResponseEntity<Report> newReportRs = restTemplate.postForEntity(
                "http://localhost:" + port + "/report",
                MOCK_REPORT_1,
                Report.class
        );

        assertThat(newReportRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Report newReport = newReportRs.getBody();

        assertThat(newReport.getDateReport()).isEqualTo(MOCK_REPORT_1.getDateReport());
        assertThat(newReport.getTextReport()).isEqualTo(MOCK_REPORT_1.getTextReport());
        assertThat(newReport.getFilePath()).isEqualTo(MOCK_REPORT_1.getFilePath());
        assertThat(newReport.getFileSize()).isEqualTo(MOCK_REPORT_1.getFileSize());
        assertThat(newReport.getMediaType()).isEqualTo(MOCK_REPORT_1.getMediaType());
        assertThat(newReport.getIsViewed()).isEqualTo(MOCK_REPORT_1.getIsViewed());
        assertThat(newReport.getAdopter()).isEqualTo(MOCK_REPORT_1.getAdopter());
        assertThat(newReport.getPet()).isEqualTo(MOCK_REPORT_1.getPet());
    }

    @Test
    public void shouldUpdateReport() throws Exception {

        createMockPet();
        createMockUser();
        createMockAdopter();
        Report createdReport = createMockReport();
        createdReport.setTextReport(MOCK_REPORT_NEW_TEXT);

        restTemplate.put(
                "http://localhost:" + port + "/report",
                createdReport,
                Report.class
        );

        ResponseEntity<Report> getReportRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/report/" + createdReport.getId(),
                Report.class
        );

        assertThat(getReportRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Report report = getReportRs.getBody();
        assertThat(report.getTextReport()).isEqualTo(createdReport.getTextReport());
    }

    @Test
    public void shouldReturnReportById() throws Exception {

        createMockPet();
        createMockUser();
        createMockAdopter();
        Report createdReport = createMockReport();

        ResponseEntity<Report> getReportRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/report/" + createdReport.getId(),
                Report.class
        );

        assertThat(getReportRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Report report = getReportRs.getBody();

        assertThat(report.getDateReport()).isEqualTo(createdReport.getDateReport());
        assertThat(report.getTextReport()).isEqualTo(createdReport.getTextReport());
        assertThat(report.getFilePath()).isEqualTo(createdReport.getFilePath());
        assertThat(report.getFileSize()).isEqualTo(createdReport.getFileSize());
        assertThat(report.getMediaType()).isEqualTo(createdReport.getMediaType());
        assertThat(report.getAdopter()).isEqualTo(createdReport.getAdopter());
        assertThat(report.getPet()).isEqualTo(createdReport.getPet());
    }

    @Test
    public void shouldReturnReportByPetIdAndDateReport() throws Exception {

        createMockPet();
        createMockUser();
        createMockAdopter();
        Report createdReport = createMockReport();

        ResponseEntity<Report> getReportRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/report/get-report-by-petId-and-date?petId=" + MOCK_PET_ID_1 +
                "&dateReport=" + MOCK_REPORT_DATE_1,
                Report.class
        );

        assertThat(getReportRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Report report = getReportRs.getBody();

        assertThat(report.getDateReport()).isEqualTo(createdReport.getDateReport());
        assertThat(report.getTextReport()).isEqualTo(createdReport.getTextReport());
        assertThat(report.getFilePath()).isEqualTo(createdReport.getFilePath());
        assertThat(report.getFileSize()).isEqualTo(createdReport.getFileSize());
        assertThat(report.getMediaType()).isEqualTo(createdReport.getMediaType());
        assertThat(report.getAdopter()).isEqualTo(createdReport.getAdopter());
        assertThat(report.getPet()).isEqualTo(createdReport.getPet());
    }

    @Test
    public void shouldDeleteReport() throws Exception {

        createMockPet();
        createMockUser();
        createMockAdopter();
        Report createdReport = createMockReport();

        restTemplate.delete(
                "http://localhost:" + port + "/report/" + createdReport.getId(),
                Report.class
        );

        ResponseEntity<Report> getReportRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/report/" + createdReport.getId(),
                Report.class
        );

        assertThat(getReportRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnAllByIsViewedNull() throws Exception {

        createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1, MOCK_PET_IS_FREE_STATUS_1);
        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2, MOCK_PET_IS_FREE_STATUS_2);
        createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3, MOCK_PET_IS_FREE_STATUS_3);

        createMockUser(1L, MOCK_USER_TELEGRAM_ID_1, MOCK_USER_CHAT_ID_1, MOCK_USER_NICK_NAME_1, MOCK_USER_LAST_NAME_1,
                MOCK_USER_FIRST_NAME_1, MOCK_USER_MIDDLE_NAME_1, MOCK_USER_PHONE_NUMBER_1, MOCK_USER_CAR_NUMBER_1,
                MOCK_USER_SHELTER_1);
        createMockUser(2L, MOCK_USER_TELEGRAM_ID_2, MOCK_USER_CHAT_ID_2, MOCK_USER_NICK_NAME_2, MOCK_USER_LAST_NAME_2,
                MOCK_USER_FIRST_NAME_2, MOCK_USER_MIDDLE_NAME_2, MOCK_USER_PHONE_NUMBER_2, MOCK_USER_CAR_NUMBER_2,
                MOCK_USER_SHELTER_2);
        createMockUser(3L, MOCK_USER_TELEGRAM_ID_3, MOCK_USER_CHAT_ID_3, MOCK_USER_NICK_NAME_3, MOCK_USER_LAST_NAME_3,
                MOCK_USER_FIRST_NAME_3, MOCK_USER_MIDDLE_NAME_3, MOCK_USER_PHONE_NUMBER_3, MOCK_USER_CAR_NUMBER_3,
                MOCK_USER_SHELTER_3);

        createMockAdopter(1L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_1);
        createMockAdopter(2L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_2);
        createMockAdopter(3L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_3);

        createMockReport(1, MOCK_REPORT_DATE_1, MOCK_REPORT_TEXT_1, MOCK_REPORT_PATH_FILE_1, MOCK_REPORT_FILE_SIZE_1,
                MOCK_REPORT_MEDIA_TYPE_1, MOCK_REPORT_IS_VIEWED_1, MOCK_ADOPTER_1, MOCK_PET_1);
        createMockReport(2, MOCK_REPORT_DATE_2, MOCK_REPORT_TEXT_2, MOCK_REPORT_PATH_FILE_2, MOCK_REPORT_FILE_SIZE_2,
                MOCK_REPORT_MEDIA_TYPE_2, MOCK_REPORT_IS_VIEWED_2, MOCK_ADOPTER_2, MOCK_PET_2);
        createMockReport(3, MOCK_REPORT_DATE_3, MOCK_REPORT_TEXT_3, MOCK_REPORT_PATH_FILE_3, MOCK_REPORT_FILE_SIZE_3,
                MOCK_REPORT_MEDIA_TYPE_3, MOCK_REPORT_IS_VIEWED_3, MOCK_ADOPTER_3, MOCK_PET_3);

        List<Report> reports = restTemplate.exchange(
                "http://localhost:" + port + "/report/all-viewed-null-reports",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Report>>() {}
        ).getBody();

        assertFalse(reports.isEmpty());
        assertEquals(reports.size(), MOCK_REPORTS_BY_IS_VIEWED_NULL.size());
        assertThat(reports).isEqualTo(MOCK_REPORTS_BY_IS_VIEWED_NULL);
    }

    @Test
    public void shouldReturnAllByAdopterId() throws Exception {

        createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1, MOCK_PET_IS_FREE_STATUS_1);
        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2, MOCK_PET_IS_FREE_STATUS_2);
        createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3, MOCK_PET_IS_FREE_STATUS_3);

        createMockUser(1L, MOCK_USER_TELEGRAM_ID_1, MOCK_USER_CHAT_ID_1, MOCK_USER_NICK_NAME_1, MOCK_USER_LAST_NAME_1,
                MOCK_USER_FIRST_NAME_1, MOCK_USER_MIDDLE_NAME_1, MOCK_USER_PHONE_NUMBER_1, MOCK_USER_CAR_NUMBER_1,
                MOCK_USER_SHELTER_1);
        createMockUser(2L, MOCK_USER_TELEGRAM_ID_2, MOCK_USER_CHAT_ID_2, MOCK_USER_NICK_NAME_2, MOCK_USER_LAST_NAME_2,
                MOCK_USER_FIRST_NAME_2, MOCK_USER_MIDDLE_NAME_2, MOCK_USER_PHONE_NUMBER_2, MOCK_USER_CAR_NUMBER_2,
                MOCK_USER_SHELTER_2);
        createMockUser(3L, MOCK_USER_TELEGRAM_ID_3, MOCK_USER_CHAT_ID_3, MOCK_USER_NICK_NAME_3, MOCK_USER_LAST_NAME_3,
                MOCK_USER_FIRST_NAME_3, MOCK_USER_MIDDLE_NAME_3, MOCK_USER_PHONE_NUMBER_3, MOCK_USER_CAR_NUMBER_3,
                MOCK_USER_SHELTER_3);

        createMockAdopter(1L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_1);
        createMockAdopter(2L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_2);
        createMockAdopter(3L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_3);

        createMockReport(1, MOCK_REPORT_DATE_1, MOCK_REPORT_TEXT_1, MOCK_REPORT_PATH_FILE_1, MOCK_REPORT_FILE_SIZE_1,
                MOCK_REPORT_MEDIA_TYPE_1, MOCK_REPORT_IS_VIEWED_1, MOCK_ADOPTER_1, MOCK_PET_1);
        createMockReport(2, MOCK_REPORT_DATE_2, MOCK_REPORT_TEXT_2, MOCK_REPORT_PATH_FILE_2, MOCK_REPORT_FILE_SIZE_2,
                MOCK_REPORT_MEDIA_TYPE_2, MOCK_REPORT_IS_VIEWED_2, MOCK_ADOPTER_2, MOCK_PET_2);
        createMockReport(3, MOCK_REPORT_DATE_3, MOCK_REPORT_TEXT_3, MOCK_REPORT_PATH_FILE_3, MOCK_REPORT_FILE_SIZE_3,
                MOCK_REPORT_MEDIA_TYPE_3, MOCK_REPORT_IS_VIEWED_3, MOCK_ADOPTER_3, MOCK_PET_3);

        List<Report> reports = restTemplate.exchange(
                "http://localhost:" + port + "/report/all-reports-by-adopterId/" + MOCK_ADOPTER_ID_1,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Report>>() {}
        ).getBody();

        assertFalse(reports.isEmpty());
        assertEquals(reports.size(), MOCK_REPORTS_BY_ADOPTER_1.size());
        assertThat(reports).isEqualTo(MOCK_REPORTS_BY_ADOPTER_1);
    }

    @Test
    public void shouldReturnAllByAdopterIdAndPetId() throws Exception {

        createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1, MOCK_PET_IS_FREE_STATUS_1);
        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2, MOCK_PET_IS_FREE_STATUS_2);
        createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3, MOCK_PET_IS_FREE_STATUS_3);

        createMockUser(1L, MOCK_USER_TELEGRAM_ID_1, MOCK_USER_CHAT_ID_1, MOCK_USER_NICK_NAME_1, MOCK_USER_LAST_NAME_1,
                MOCK_USER_FIRST_NAME_1, MOCK_USER_MIDDLE_NAME_1, MOCK_USER_PHONE_NUMBER_1, MOCK_USER_CAR_NUMBER_1,
                MOCK_USER_SHELTER_1);
        createMockUser(2L, MOCK_USER_TELEGRAM_ID_2, MOCK_USER_CHAT_ID_2, MOCK_USER_NICK_NAME_2, MOCK_USER_LAST_NAME_2,
                MOCK_USER_FIRST_NAME_2, MOCK_USER_MIDDLE_NAME_2, MOCK_USER_PHONE_NUMBER_2, MOCK_USER_CAR_NUMBER_2,
                MOCK_USER_SHELTER_2);
        createMockUser(3L, MOCK_USER_TELEGRAM_ID_3, MOCK_USER_CHAT_ID_3, MOCK_USER_NICK_NAME_3, MOCK_USER_LAST_NAME_3,
                MOCK_USER_FIRST_NAME_3, MOCK_USER_MIDDLE_NAME_3, MOCK_USER_PHONE_NUMBER_3, MOCK_USER_CAR_NUMBER_3,
                MOCK_USER_SHELTER_3);

        createMockAdopter(1L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_1);
        createMockAdopter(2L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_2);
        createMockAdopter(3L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_3);

        createMockReport(1, MOCK_REPORT_DATE_1, MOCK_REPORT_TEXT_1, MOCK_REPORT_PATH_FILE_1, MOCK_REPORT_FILE_SIZE_1,
                MOCK_REPORT_MEDIA_TYPE_1, MOCK_REPORT_IS_VIEWED_1, MOCK_ADOPTER_1, MOCK_PET_1);
        createMockReport(2, MOCK_REPORT_DATE_2, MOCK_REPORT_TEXT_2, MOCK_REPORT_PATH_FILE_2, MOCK_REPORT_FILE_SIZE_2,
                MOCK_REPORT_MEDIA_TYPE_2, MOCK_REPORT_IS_VIEWED_2, MOCK_ADOPTER_2, MOCK_PET_2);
        createMockReport(3, MOCK_REPORT_DATE_3, MOCK_REPORT_TEXT_3, MOCK_REPORT_PATH_FILE_3, MOCK_REPORT_FILE_SIZE_3,
                MOCK_REPORT_MEDIA_TYPE_3, MOCK_REPORT_IS_VIEWED_3, MOCK_ADOPTER_3, MOCK_PET_3);

        List<Report> reports = restTemplate.exchange(
                "http://localhost:" + port + "/report/all-reports-by-adopterId-and-petId?adopterId=" +
                        MOCK_ADOPTER_ID_2 + "&petId=" + MOCK_PET_ID_2,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Report>>() {}
        ).getBody();

        assertFalse(reports.isEmpty());
        assertEquals(reports.size(), MOCK_REPORTS_BY_ADOPTER_2_AND_PET_2.size());
        assertThat(reports).isEqualTo(MOCK_REPORTS_BY_ADOPTER_2_AND_PET_2);
    }

    @Test
    public void shouldDeleteAllByAdopterId() throws Exception {

        createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1, MOCK_PET_IS_FREE_STATUS_1);
        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2, MOCK_PET_IS_FREE_STATUS_2);
        createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3, MOCK_PET_IS_FREE_STATUS_3);

        createMockUser(1L, MOCK_USER_TELEGRAM_ID_1, MOCK_USER_CHAT_ID_1, MOCK_USER_NICK_NAME_1, MOCK_USER_LAST_NAME_1,
                MOCK_USER_FIRST_NAME_1, MOCK_USER_MIDDLE_NAME_1, MOCK_USER_PHONE_NUMBER_1, MOCK_USER_CAR_NUMBER_1,
                MOCK_USER_SHELTER_1);
        createMockUser(2L, MOCK_USER_TELEGRAM_ID_2, MOCK_USER_CHAT_ID_2, MOCK_USER_NICK_NAME_2, MOCK_USER_LAST_NAME_2,
                MOCK_USER_FIRST_NAME_2, MOCK_USER_MIDDLE_NAME_2, MOCK_USER_PHONE_NUMBER_2, MOCK_USER_CAR_NUMBER_2,
                MOCK_USER_SHELTER_2);
        createMockUser(3L, MOCK_USER_TELEGRAM_ID_3, MOCK_USER_CHAT_ID_3, MOCK_USER_NICK_NAME_3, MOCK_USER_LAST_NAME_3,
                MOCK_USER_FIRST_NAME_3, MOCK_USER_MIDDLE_NAME_3, MOCK_USER_PHONE_NUMBER_3, MOCK_USER_CAR_NUMBER_3,
                MOCK_USER_SHELTER_3);

        createMockAdopter(1L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_1);
        createMockAdopter(2L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_2);
        createMockAdopter(3L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_3);

        createMockReport(1, MOCK_REPORT_DATE_1, MOCK_REPORT_TEXT_1, MOCK_REPORT_PATH_FILE_1,
                MOCK_REPORT_FILE_SIZE_1, MOCK_REPORT_MEDIA_TYPE_1, MOCK_REPORT_IS_VIEWED_1, MOCK_ADOPTER_1, MOCK_PET_1);
        createMockReport(2, MOCK_REPORT_DATE_2, MOCK_REPORT_TEXT_2, MOCK_REPORT_PATH_FILE_2,
                MOCK_REPORT_FILE_SIZE_2, MOCK_REPORT_MEDIA_TYPE_2, MOCK_REPORT_IS_VIEWED_2, MOCK_ADOPTER_2, MOCK_PET_2);
        createMockReport(3, MOCK_REPORT_DATE_3, MOCK_REPORT_TEXT_3, MOCK_REPORT_PATH_FILE_3,
                MOCK_REPORT_FILE_SIZE_3, MOCK_REPORT_MEDIA_TYPE_3, MOCK_REPORT_IS_VIEWED_3, MOCK_ADOPTER_3, MOCK_PET_3);

        restTemplate.delete(
                "http://localhost:" + port + "/report/delete-all-reports-by-adopterId/" + MOCK_ADOPTER_ID_1,
                Report.class
        );

        List<Report> reports = restTemplate.exchange(
                "http://localhost:" + port + "/report/all-reports-by-adopterId/" + MOCK_ADOPTER_ID_1,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Report>>() {}
        ).getBody();

        assertTrue(reports.isEmpty());
    }

    @Test
    public void shouldMarkReport() throws Exception {

        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2, MOCK_PET_IS_FREE_STATUS_2);
        createMockUser(2L, MOCK_USER_TELEGRAM_ID_2, MOCK_USER_CHAT_ID_2, MOCK_USER_NICK_NAME_2, MOCK_USER_LAST_NAME_2,
                MOCK_USER_FIRST_NAME_2, MOCK_USER_MIDDLE_NAME_2, MOCK_USER_PHONE_NUMBER_2, MOCK_USER_CAR_NUMBER_2,
                MOCK_USER_SHELTER_2);
        createMockAdopter(2L, MOCK_ADOPTER_PASSED_PROBATION_PERIOD_2);
        Report createdReport = createMockReport(2, MOCK_REPORT_DATE_2, MOCK_REPORT_TEXT_2, MOCK_REPORT_PATH_FILE_2,
                MOCK_REPORT_FILE_SIZE_2, MOCK_REPORT_MEDIA_TYPE_2, MOCK_REPORT_IS_VIEWED_2, MOCK_ADOPTER_2, MOCK_PET_2);

        createdReport.setIsViewed(true);

        restTemplate.put(
                "http://localhost:" + port + "/report/mark/" + createdReport.getId(),
                createdReport,
                Report.class
        );

        ResponseEntity<Report> getReportRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/report/" + createdReport.getId(),
                Report.class
        );

        assertThat(getReportRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Report report = getReportRs.getBody();
        assertThat(report.getIsViewed()).isEqualTo(createdReport.getIsViewed());
    }







    public Report createMockReport() {
        return reportService.createReport(MOCK_REPORT_1);
    }

    public Pet createMockPet() {
        return petService.createPet(MOCK_PET_1);
    }

    public void createMockUser() {
        telegramUserService.save(MOCK_USER_1);
    }

    public Adopter createMockAdopter() {
        return adopterService.createAdopter(MOCK_ADOPTER_1);
    }

    public Report createMockReport(Integer id, LocalDate dateReport, String textReport, String filePath, long fileSize,
                                   String mediaType, Boolean isViewed, Adopter adopter, Pet pet) {
        return reportService.createReport(new Report(id, dateReport, textReport, filePath, fileSize,
                mediaType, isViewed, adopter, pet));
    }

    public Pet createMockPet(Long id, PetType petType, String name, Sex sex, Integer age, Boolean isFreeStatus) {
        return petService.createPet(new Pet(id, petType, name, sex, age, isFreeStatus));
    }

    public void createMockUser(Long id, Long telegramId, Long chatId, String nickName, String lastName, String firstName,
                          String middleName, String phoneNumber, String carNumber, String shelter) {
        telegramUserService.save(new TelegramUser(id, telegramId, chatId, nickName, lastName, firstName,
                middleName, phoneNumber, carNumber, shelter));
    }

    public Adopter createMockAdopter(Long id, boolean passedTheProbationPeriod) {
        return adopterService.createAdopter(new Adopter(id, passedTheProbationPeriod));
    }



}
