package com.example.dev_pro.controller;


import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/shelter")
@Tag(name = "API для работы с приютами")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService service;

    @PostMapping
    @Operation(
            summary = "Создание приюта",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый приют. При создании приюта и сохранении его в базу данных " +
                            "переданный идентификатор будет проигнорирован, и приюту будет присвоен идентификатор, " +
                            "следующий за идентификатором последнего приюта в базе данных"
            )
    )
    public ResponseEntity<Shelter> createShelter(@RequestBody Shelter shelter) {
        Shelter sh = service.createShelter(shelter);
        return ResponseEntity.ok(sh);
    }

    @PutMapping
    @Operation(
            summary = "Обновление приюта",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый приют. Данные о приюте изложены в формате JSON"
            )
    )
    public ResponseEntity<Shelter> updateShelter(@RequestBody Shelter shelter) {
        Shelter sh = service.updateShelter(shelter);
        return ResponseEntity.ok(sh);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск приюта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если приют не найден"
                    )
            }
    )
    public ResponseEntity<Shelter> getShelterById(@PathVariable("id") Integer id) {
        Shelter sh = service.findShelterById(id);
        return ResponseEntity.ok(sh);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление приюта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если приют не найден"
                    )
            }
    )
    public ResponseEntity<Shelter> deleteShelterById(@PathVariable("id") Integer id) {
        Shelter sh = service.deleteShelterById(id);
        return ResponseEntity.ok(sh);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех приютов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные приюты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            }
    )
    public ResponseEntity<Collection<Shelter>> getAllShelters() {
        Collection<Shelter> shelters = service.findAllShelters();
        return ResponseEntity.ok(shelters);
    }

    @GetMapping("/all-pets-by-shelter/{shelterId}")
    @Operation(
            summary = "Получение всех питомцев приюта по его идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные питомцы",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            }
    )
    public ResponseEntity<Collection<Pet>> getPetsByShelterId(@PathVariable("shelterId") Integer shelterId) {
        Collection<Pet> pets = service.findPetsByShelterId(shelterId);
        return ResponseEntity.ok(pets);
    }

}
