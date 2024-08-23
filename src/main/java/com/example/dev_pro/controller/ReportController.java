package com.example.dev_pro.controller;


import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.ReportService;
import com.example.dev_pro.service.TelegramUserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
@Tag(name = "API для работы с отчетами")
@RequiredArgsConstructor
public class ReportController {

    private static final String MESSAGE_ABOUT_IMPROPER_REPORTING = "Дорогой усыновитель, мы заметили, что ты заполняешь " +
            "отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном " +
            "случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.";

    private final ReportService reportService;
    private final AdopterService adopterService;
    private final TelegramUserService telegramUserService;
    private final TelegramBot telegramBot;



    @PostMapping
    @Operation(
            summary = "Создание отчета",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый отчет. При создании отчета и сохранении его в базу данных " +
                            "переданный идентификатор будет проигнорирован, и отчету будет присвоен идентификатор, " +
                            "следующий за идентификатором последнего отчета в базе данных"
            )
    )
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report rep = reportService.createReport(report);
        return ResponseEntity.ok(rep);
    }


    @PutMapping
    @Operation(
            summary = "Обновление отчета",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый отчет. Данные об отчете изложены в формате JSON"
            )
    )
    public ResponseEntity<Report> updateReport(@RequestBody Report report) {
        Report rep = reportService.updateReport(report);
        return ResponseEntity.ok(rep);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Выгрузка отчета по его идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Выгруженный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если отчет не найден"
                    )
            }
    )
    public ResponseEntity<Report> getReportById(
            @Parameter(description = "Идентификатор отчета", example = "2")
            @PathVariable Integer id) {
        Report rep = reportService.findReportById(id);
        return ResponseEntity.ok(rep);
    }


    @GetMapping("/get-report-by-petId-and-date")
    @Operation(
            summary = "Поиск отчета усыновителя по идентификатору питомца и дате",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    public ResponseEntity<Report> getReportByPetIdAndDateReport(
            @RequestParam Long petId,
            @RequestParam LocalDate dateReport
    ) {
        Report report = reportService.findReportByPetIdAndDateReport(petId, dateReport);
        return ResponseEntity.ok(report);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если отчет не найден"
                    )
            }
    )
    public ResponseEntity<Report> deleteReportById(
            @Parameter(description = "Идентификатор отчета", example = "4")
            @PathVariable Integer id
    ) {
        Report rep = reportService.deleteReportById(id);
        return ResponseEntity.ok(rep);
    }


    @GetMapping("/all-viewed-null-reports")
    @Operation(
            summary = "Получение списка непросмотренных отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные непросмотренные отчеты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<Report>> getAllByIsViewedNull() {
        List<Report> reports = reportService.findAllByIsViewedNull();
        if (reports == null || reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/all-reports-by-adopterId/{id}")
    @Operation(
            summary = "Получение списка отчетов по идентификатору усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные отчеты конкретного усыновителя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<Report>> getAllByAdopterId(@PathVariable("id") Long adopterId) {
        List<Report> reports = reportService.findAllByAdopterId(adopterId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/all-reports-by-adopterId-and-petId")
    @Operation(
            summary = "Получение списка отчетов по идентификатору усыновителя и идентификатору питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные отчеты конкретного усыновителя и по конкретному питомцу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<Report>> getAllByAdopterIdAndPetId(
            @RequestParam Long adopterId,
            @RequestParam Long petId
    ) {
        List<Report> reports = reportService.findAllByAdopterIdAndPetId(adopterId, petId);
        return ResponseEntity.ok(reports);
    }

    @DeleteMapping("/delete-all-reports-by-adopterId/{id}")
    @Operation(
            summary = "Удаление всех отчетов по идентификатору усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные  отчеты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    public ResponseEntity<Void> deleteAllByAdopterId(@PathVariable("id") Adopter adopterId) {
        reportService.deleteAllByAdopterId(adopterId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/mark/{id}")
    @Operation(
            summary = "Проставляем отметку, что отчет просмотрен",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отмеченный отчет"
                    )
            }
    )
    public ResponseEntity<Report> markReport(@PathVariable Integer id) {
        Report report = reportService.findReportById(id);
        report.setIsViewed(true);
        reportService.updateReport(report);
        return ResponseEntity.ok(report);
    }


    @PostMapping("/send-a-message/{adopterId}")
    @Operation(
            summary = "Отправка сообщения о ненадлежащем сданном отчете.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение отправлено"
                    )
            }
    )
    public ResponseEntity<?> messageToAdopter(@RequestBody Long adopterId) {
        Adopter adopter = adopterService.findAdopterById(adopterId);
        // Осуществляем поиск усыновителя по его идентификатору
        TelegramUser telegramUser = telegramUserService.findTelegramUserByAdopter(adopter);
        // Осуществляем поиск пользователя телеграм по усыновителю
        Long chatId = telegramUser.getChatId();
        SendMessage sendMessage = new SendMessage(chatId, MESSAGE_ABOUT_IMPROPER_REPORTING);
        if (!telegramBot.execute(sendMessage).isOk()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
