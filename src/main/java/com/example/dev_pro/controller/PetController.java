package com.example.dev_pro.controller;

import com.example.dev_pro.dto.PetDto;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/pet")
@Tag(name = "API для работы с питомцами")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    @Operation(
            summary = "Создание питомца",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый питомец. При создании питомца и сохранении его в базу данных " +
                            "переданный идентификатор будет проигнорирован, и питомцу будет присвоен идентификатор, " +
                            "следующий за идентификатором последнего питомца в базе данных"
            )
    )
    public ResponseEntity<Pet> createPet(@RequestBody PetDto petDto) {
        Pet createdPet = petService.createPet(petDto);
        return ResponseEntity.ok(createdPet);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление питомца",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновляемый питомец. Данные о питомце изложены в формате JSON"
            )
    )
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        Pet updatedPet = petService.updatePet(id, petDto);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение питомца по идентификатору",
            description = "Выгрузка питомца по его идентификатору"
    )
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        Pet pet = petService.findPetById(id);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление питомца по идентификатору",
            description = "Удаление питомца из базы данных по его идентификатору"
    )
    public ResponseEntity<Pet> deletePetById(@PathVariable("id") Long id) {
        Pet pet = petService.deletePetById(id);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех питомцев",
            description = "Получение списка всех питомцев из базы данных"
    )
    public ResponseEntity<Collection<Pet>> getAllPets() {
        Collection<Pet> pets = petService.findAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-pets-by-shelter/{shelterId}")
    @Operation(
            summary = "Получение всех питомцев по идентификатору приюта",
            description = "Получение списка всех питомцев, находящихся в указанном приюте"
    )
    public ResponseEntity<List<Pet>> getAllByShelterId(@PathVariable("shelterId") Integer shelterId) {
        List<Pet> pets = petService.findAllByShelterId(shelterId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-free-pets-by-shelter")
    @Operation(
            summary = "Получение всех свободных питомцев по идентификатору приюта",
            description = "Получение списка всех свободных питомцев, находящихся в указанном приюте"
    )
    public ResponseEntity<List<Pet>> getAllByShelterIdAndIsFreeStatus(
            @RequestParam Integer shelterId,
            @RequestParam boolean isFreeStatus
    ) {
        List<Pet> pets = petService.findAllByShelterIdAndIsFreeStatus(shelterId, isFreeStatus);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-pets-by-adopter/{adopterId}")
    @Operation(
            summary = "Получение всех питомцев по идентификатору усыновителя",
            description = "Получение списка всех питомцев, закрепленных за указанным усыновителем"
    )
    public ResponseEntity<List<Pet>> getAllByAdopterId(@PathVariable("adopterId") Long adopterId) {
        List<Pet> pets = petService.findAllByAdopterId(adopterId);
        return ResponseEntity.ok(pets);
    }
}