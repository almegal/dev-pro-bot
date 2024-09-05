package com.example.dev_pro.controller;


import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.TelegramUserService;
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

import java.util.List;

@RestController
@RequestMapping("/telegram_user")
@Tag(name = "API для работы с пользователями телеграм")
@RequiredArgsConstructor
public class TelegramUserController {

    private final TelegramUserService service;

    @PostMapping
    @Operation(
            summary = "Создание пользователя телеграм",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый пользователь телеграм. При создании пользователя телеграм и сохранении " +
                            "его в базу данных переданный идентификатор будет проигнорирован, и пользователю будет " +
                            "присвоен идентификатор, следующий за идентификатором последнего пользователя в базе данных"
            )
    )
    public ResponseEntity<Void> createUser(@RequestBody TelegramUser user) {
        service.save(user);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    @Operation(
            summary = "Обновление пользователя телеграм",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый пользователь телеграм. Данные о пользователе изложены в формате JSON"
            )
    )
    public ResponseEntity<Void> updateUser(@RequestBody TelegramUser user) {
        service.update(user);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь телеграм",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TelegramUser.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если пользователь не найден"
                    )
            }
    )
    public ResponseEntity<TelegramUser> getUserById(
            @Parameter(description = "Идентификатор пользователя", example = "2")
            @PathVariable Long id) {
        TelegramUser tu = service.getById(id);
        if (tu == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tu);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех пользователей телеграм",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TelegramUser.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если пользователи не найдены"
                    )
            }
    )
    public ResponseEntity<List<TelegramUser>> getAll() {
        List<TelegramUser> telegramUserList = service.getAll();
        if (telegramUserList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(telegramUserList);
    }


}
