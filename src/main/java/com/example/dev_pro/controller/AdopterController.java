package com.example.dev_pro.controller;

import com.example.dev_pro.dto.AdopterDTO;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.service.AdopterService;
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

@RestController
@RequestMapping("/adopter")
@Tag(name = "API для работы с усыновителями")
@RequiredArgsConstructor
public class AdopterController {

    private final AdopterService adopterService;


    @PostMapping
    @Operation(
            summary = "Создание усыновителя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый усыновитель. При создании усыновителя и сохранении его в базу данных " +
                            "переданный идентификатор будет проигнорирован, и усыновителю будет присвоен идентификатор, " +
                            "следующий за идентификатором последнего усыновителя в базе данных"
            )
    )
    public ResponseEntity<Adopter> createAdopter(@RequestBody AdopterDTO adopter) {
        Adopter adop = adopterService.create(adopter);
        return ResponseEntity.ok(adop);
    }


    @PutMapping
    @Operation(
            summary = "Обновление усыновителя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый усыновитель. Данные об усыновителе изложены в формате JSON"
            )
    )
    public ResponseEntity<Adopter> updateAdopter(@RequestBody Adopter adopter) {
        Adopter adop = adopterService.update(adopter);
        return ResponseEntity.ok(adop);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск усыновителя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный усыновитель",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Adopter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если усыновитель не найден"
                    )
            }
    )
    public ResponseEntity<Adopter> getAdopterById(
            @Parameter(description = "Идентификатор усыновителя", example = "2")
            @PathVariable Long id) {
        Adopter adop = adopterService.findAdopterById(id);
        return ResponseEntity.ok(adop);
    }


    @GetMapping("/get-adopter-by-telegram_user/{id}")
    @Operation(
            summary = "Поиск усыновителя по идентификатору пользователя телеграм",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный усыновитель",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Adopter.class))
                            )
                    )
            }
    )
    public ResponseEntity<Adopter> getAdopterByTelegramUserId(@PathVariable("id") Long telegramUserId) {
        Adopter adopter = adopterService.findAdopterByTelegramUserId(telegramUserId);
        return ResponseEntity.ok(adopter);
    }
}
