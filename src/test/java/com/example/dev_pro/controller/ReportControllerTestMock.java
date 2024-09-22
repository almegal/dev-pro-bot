package com.example.dev_pro.controller;

import com.example.dev_pro.impl.AdopterServiceImpl;
import com.example.dev_pro.impl.PetServiceImpl;
import com.example.dev_pro.impl.ReportServiceImpl;
import com.example.dev_pro.impl.TelegramUserServiceImpl;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.repository.AdopterRepository;
import com.example.dev_pro.repository.PetRepository;
import com.example.dev_pro.repository.ReportRepository;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.TelegramUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pengrad.telegrambot.TelegramBot;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.dev_pro.controller.ReportTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReportController.class)
public class ReportControllerTestMock {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private AdopterRepository adopterRepository;

    @MockBean
    private TelegramUserRepository telegramUserRepository;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private TelegramBot telegramBot;

    @SpyBean
    private ReportServiceImpl reportService;

    @SpyBean
    private PetServiceImpl petService;

    @SpyBean
    private AdopterServiceImpl adopterService;

    @SpyBean
    private TelegramUserServiceImpl telegramUserService;

    @InjectMocks
    private ReportController reportController;

    private ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    // С помощью данного класса можно сериализовать объект в JSON
    // необходим для теста shouldAllReports()


    @Test
    public void shouldCreateReport() throws Exception {

        when(reportRepository.save(any(Report.class))).thenReturn(MOCK_REPORT_1);
        // должен вернуться MOCK_REPORT

        // Создадим переменную reportObject и заполним ее данными. Эту переменную будем использовать в качестве входных данных.
        JSONObject reportObject = new JSONObject();
        reportObject.put("dateReport", MOCK_REPORT_DATE_1);
        reportObject.put("textReport", MOCK_REPORT_TEXT_1);
        reportObject.put("filePath", MOCK_REPORT_PATH_FILE_1);
        reportObject.put("fileSize", MOCK_REPORT_FILE_SIZE_1);
        reportObject.put("mediaType", MOCK_REPORT_MEDIA_TYPE_1);
        reportObject.put("isViewed", MOCK_REPORT_IS_VIEWED_1);

        // с помощью метода perform() осуществляем вызов эндпоинта
        mockmvc.perform(MockMvcRequestBuilders
                        .post("/report")
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateReport").value(MOCK_REPORT_DATE_1.toString()))
                .andExpect(jsonPath("$.textReport").value(MOCK_REPORT_TEXT_1))
                .andExpect(jsonPath("$.filePath").value(MOCK_REPORT_PATH_FILE_1))
                .andExpect(jsonPath("$.fileSize").value(MOCK_REPORT_FILE_SIZE_1))
                .andExpect(jsonPath("$.mediaType").value(MOCK_REPORT_MEDIA_TYPE_1))
                .andExpect(jsonPath("$.isViewed").value(MOCK_REPORT_IS_VIEWED_1));
    }

    @Test
    public void shouldUpdateReport() throws Exception {

        when(reportRepository.save(any(Report.class))).thenReturn(MOCK_REPORT_1);

        JSONObject reportObject = new JSONObject();
        reportObject.put("dateReport", MOCK_REPORT_DATE_1);
        reportObject.put("textReport", MOCK_REPORT_TEXT_1);
        reportObject.put("filePath", MOCK_REPORT_PATH_FILE_1);
        reportObject.put("fileSize", MOCK_REPORT_FILE_SIZE_1);
        reportObject.put("mediaType", MOCK_REPORT_MEDIA_TYPE_1);
        reportObject.put("isViewed", MOCK_REPORT_IS_VIEWED_1);

        mockmvc.perform(MockMvcRequestBuilders
                        .put("/report")
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateReport").value(MOCK_REPORT_DATE_1.toString()))
                .andExpect(jsonPath("$.textReport").value(MOCK_REPORT_TEXT_1))
                .andExpect(jsonPath("$.filePath").value(MOCK_REPORT_PATH_FILE_1))
                .andExpect(jsonPath("$.fileSize").value(MOCK_REPORT_FILE_SIZE_1))
                .andExpect(jsonPath("$.mediaType").value(MOCK_REPORT_MEDIA_TYPE_1))
                .andExpect(jsonPath("$.isViewed").value(MOCK_REPORT_IS_VIEWED_1));
    }

    @Test
    public void shouldReturnReportById() throws Exception {

        when(reportRepository.findById(any(Integer.class))).thenReturn(Optional.of(MOCK_REPORT_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/report/" + MOCK_REPORT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateReport").value(MOCK_REPORT_DATE_1.toString()))
                .andExpect(jsonPath("$.textReport").value(MOCK_REPORT_TEXT_1))
                .andExpect(jsonPath("$.filePath").value(MOCK_REPORT_PATH_FILE_1))
                .andExpect(jsonPath("$.fileSize").value(MOCK_REPORT_FILE_SIZE_1))
                .andExpect(jsonPath("$.mediaType").value(MOCK_REPORT_MEDIA_TYPE_1))
                .andExpect(jsonPath("$.isViewed").value(MOCK_REPORT_IS_VIEWED_1));
    }

    @Test
    public void shouldReturnReportByPetIdAndDateReport() throws Exception {

        MOCK_REPORT_1.setPet(MOCK_PET_1);
        MOCK_REPORT_2.setPet(MOCK_PET_2);

        when(reportRepository.findReportByPetIdAndDateReport(any(Long.class), any(LocalDate.class)))
                .thenReturn(Optional.of(MOCK_REPORT_2));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/report/get-report-by-petId-and-date?petId=" + MOCK_PET_ID_2
                                + "&dateReport=" + MOCK_REPORT_DATE_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateReport").value(MOCK_REPORT_DATE_2.toString()))
                .andExpect(jsonPath("$.textReport").value(MOCK_REPORT_TEXT_2))
                .andExpect(jsonPath("$.filePath").value(MOCK_REPORT_PATH_FILE_2))
                .andExpect(jsonPath("$.fileSize").value(MOCK_REPORT_FILE_SIZE_2))
                .andExpect(jsonPath("$.mediaType").value(MOCK_REPORT_MEDIA_TYPE_2))
                .andExpect(jsonPath("$.isViewed").value(MOCK_REPORT_IS_VIEWED_2));
    }

    @Test
    public void shouldDeleteStudent() throws Exception {

        when(reportRepository.findById(any(Integer.class))).thenReturn(Optional.of(MOCK_REPORT_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .delete("/report/" + MOCK_REPORT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllByIsViewedNull() throws Exception {

        when(reportRepository.findAllByIsViewedNull()).thenReturn(MOCK_REPORTS_BY_IS_VIEWED_NULL);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/report/all-viewed-null-reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_REPORTS_BY_IS_VIEWED_NULL)));
        // Проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода списка отчетов
    }

//    @Test
//    public void shouldReturnAllStudents() throws Exception {
//        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/all")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
//        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
//        // ObjectMapper метода writeValueAsString() и передачи в параметры метода списка студентов
//        // в результате вызова эндпоинта "/student/all" должен вернуться список студентов - MOCK_STUDENTS
//    }
//
//    @Test
//    public void shouldReturnStudentsByAge() throws Exception {
//        when(studentRepository.findByAgeBetween(anyInt(), anyInt()))
//                .thenReturn(MOCK_STUDENTS_BY_AGE);
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/get-by/" + (MOCK_STUDENT_AGE_2) + "/" + (MOCK_STUDENT_AGE_3))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS_BY_AGE)));
//    }
//    // данный тест выполняется при любых значениях минимального и максимального возраста
//
//    @Test
//    public void shouldReturnFacultyOfStudents() throws Exception {
//
//        MOCK_STUDENT_3.setFaculty(MOCK_FACULTY_3);
//        // у объекта типа Student вызываем сеттер и инициализируем его поле - факультет
//
//        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT_3));
//        // должен быть возвращен объект типа Student с привязанным к нему факультетом
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/faculty/" + MOCK_STUDENT_ID_3)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTY_3)));
//        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
//        // ObjectMapper метода writeValueAsString() и передачи в параметры метода установленного факультета
//    }
//
//
//    // получение количества всех студентов
//    @Test
//    public void  shouldReturnTotalCountOfStudents() throws Exception {
//
//        when(studentRepository.getTotalCountOfStudents()).thenReturn(MOCK_STUDENTS.size());
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/count")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS.size())));
//    }
//
//
//    // получение среднего возраста студентов
//    @Test
//    public void shouldReturnAverageAgeOfStudents() throws Exception {
//
//        when(studentRepository.getAverageAgeOfStudents())
//                .thenReturn((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3) / 3));
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/average-age")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 +
//                        MOCK_STUDENT_AGE_3) / 3))));
//    }
//
//
//    // получение пяти последних студентов
//    @Test
//    public void shouldReturnLimitOfStudents() throws Exception {
//
//        when(studentRepository.getLastFive()).thenReturn(MOCK_STUDENTS_LIMIT);
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/last-five")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS_LIMIT)));
//    }
//
//
//    // получение количества всех студентов через создание interface projection
//    @Test
//    public void shouldReturnAmountOfStudents() throws Exception {
//
//        when(allStudentsRepository.getAmountOfStudents()).thenReturn(MOCK_STUDENTS.size());
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/amount")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS.size())));
//    }
//
//
//    @Test
//    public void shouldReturnStudentNamesStartingWithA() throws Exception {
//
//        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);
//
//        when(studentService.getStudentNamesStartingWithA())
//                .thenReturn(MOCK_STUDENT_NAMES_WITH_STARTING_A);
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/names-by-a")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENT_NAMES_WITH_STARTING_A)));
//    }
//
//    // получение среднего возраста студентов через stream
//    @Test
//    public void shouldReturnAverageAgeOfStudentsByStream() throws Exception {
//
//        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);
//
//        when(studentService.getAverageAgeByStream())
//                .thenReturn((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3) / 3));
//
//        mockmvc.perform(MockMvcRequestBuilders
//                        .get("/student/average-age-stream")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 +
//                        MOCK_STUDENT_AGE_3) / 3))));
//    }


}
