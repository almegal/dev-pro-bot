package com.example.dev_pro.controller;


import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.VolunteerService;
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

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/volunteer")
@Tag(name = "API для работы с волонтерами")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;

    @PostMapping
    @Operation(
            summary = "Создание волонтера",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый волонтер. При создании волонтера и сохранении его в базу данных " +
                            "переданный идентификатор будет проигнорирован, и волонтеру будет присвоен идентификатор, " +
                            "следующий за идентификатором последнего волонтера в базе данных"
            )
    )
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer vol = volunteerService.createVolunteer(volunteer);
        return ResponseEntity.ok(vol);
    }


    @PutMapping
    @Operation(
            summary = "Обновление волонтера",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый волонтер. Данные о волонтере изложены в формате JSON"
            )
    )
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer vol = volunteerService.updateVolunteer(volunteer);
        return ResponseEntity.ok(vol);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если волонтер не найден"
                    )
            }
    )
    public ResponseEntity<Volunteer> getVolunteerById(
            @Parameter(description = "Идентификатор волонтера", example = "2")
            @PathVariable Long id) {
        Volunteer vol = volunteerService.findVolunteerById(id);
        return ResponseEntity.ok(vol);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Удаление волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если волонтер не найден"
                    )
            }
    )
    public ResponseEntity<Volunteer> deleteVolunteerById(
            @Parameter(description = "Идентификатор волонтера", example = "4")
            @PathVariable Long id
    ) {
        Volunteer vol = volunteerService.deleteVolunteerById(id);
        return ResponseEntity.ok(vol);
    }


    @GetMapping("/all")
    @Operation(
            summary = "Получение всех волонтеров",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные волонтеры",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            }
    )
    public ResponseEntity<Collection<Volunteer>> getAllVolunteers() {
        Collection<Volunteer> volunteers = volunteerService.findAllVolunteers();
        return ResponseEntity.ok(volunteers);
    }


    @GetMapping("/nicknames")
    @Operation(
            summary = "Получение списка никнеймов волонтеров",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список никнеймов найденных волонтеров",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<String>> getListNickNamesOfVolunteers() {
        List<String> nickNames = volunteerService.getListNickNamesOfVolunteers();
        return ResponseEntity.ok(nickNames);
    }

    @GetMapping("/chat-id-volunteer")
    @Operation(
            summary = "Получение идентификатора чата волонтера по никнейму",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Идентификатор чата волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            }
    )
    public ResponseEntity<Long> getChatIdOfVolunteer(@RequestParam (name = "Никнейм волонтера") String nickName) {
        Long chatIdVolunteer = volunteerService.getChatIdOfVolunteer(nickName);
        return ResponseEntity.ok(chatIdVolunteer);
    }

}
