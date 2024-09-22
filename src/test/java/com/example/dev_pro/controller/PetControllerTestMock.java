package com.example.dev_pro.controller;

import com.example.dev_pro.impl.*;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

import static com.example.dev_pro.controller.ReportTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PetController.class)
public class PetControllerTestMock {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private PetRepository petRepository;

    @SpyBean
    private PetServiceImpl petService;

    @InjectMocks
    private PetController petController;

    private ObjectMapper mapper = new ObjectMapper();
    // с помощью данного класса можно сериализовать объект в JSON
    // необходим для теста shouldAllPets()


    @Test
    public void shouldCreatePet() throws Exception {

        when(petRepository.save(any(Pet.class))).thenReturn(MOCK_PET_1);
        // должен вернуться MOCK_PET

        // Создадим переменную reportObject и заполним ее данными. Эту переменную будем использовать в качестве входных данных.
        JSONObject petObject = new JSONObject();
        petObject.put("petType", MOCK_PET_TYPE_1.name());
        petObject.put("name", MOCK_PET_NAME_1);
        petObject.put("sex", MOCK_PET_SEX_1);
        petObject.put("age", MOCK_PET_AGE_1);
        petObject.put("isFreeStatus", MOCK_PET_IS_FREE_STATUS_1);

        // с помощью метода perform() осуществляем вызов эндпоинта
        mockmvc.perform(MockMvcRequestBuilders
                        .post("/pet")
                        .content(petObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // проверяем, что статус ответа 200
                .andExpect(jsonPath("$.petType").value(MOCK_PET_TYPE_1.name()))
                // проверяем, что тип питомца в json соответствует типу питомца MOCK_PET_TYPE_1
                .andExpect(jsonPath("$.name").value(MOCK_PET_NAME_1))
        // проверяем, что имя в json соответствует имени MOCK_PET_NAME_1, то есть тому, который передали
                .andExpect(jsonPath("$.sex").value(MOCK_PET_SEX_1.name()))
                .andExpect(jsonPath("$.age").value(MOCK_PET_AGE_1))
                .andExpect(jsonPath("$.isFreeStatus").value(MOCK_PET_IS_FREE_STATUS_1));
        // при помощи $ извлекаем из json значение соответствующего поля и сравниваем с входящими данными
    }

    @Test
    public void shouldUpdatePet() throws Exception {

        when(petRepository.save(any(Pet.class))).thenReturn(MOCK_PET_1);

        JSONObject petObject = new JSONObject();
        petObject.put("id", MOCK_PET_ID_1);
        petObject.put("petType", MOCK_PET_TYPE_1);
        petObject.put("name", MOCK_PET_NAME_1);
        petObject.put("sex", MOCK_PET_SEX_1);
        petObject.put("age", MOCK_PET_AGE_1);
        petObject.put("isFreeStatus", MOCK_PET_IS_FREE_STATUS_1);

        mockmvc.perform(MockMvcRequestBuilders
                        .put("/pet")
                        .content(petObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petType").value(MOCK_PET_TYPE_1.name()))
                .andExpect(jsonPath("$.name").value(MOCK_PET_NAME_1))
                .andExpect(jsonPath("$.sex").value(MOCK_PET_SEX_1.name()))
                .andExpect(jsonPath("$.age").value(MOCK_PET_AGE_1))
                .andExpect(jsonPath("$.isFreeStatus").value(MOCK_PET_IS_FREE_STATUS_1));
    }

    @Test
    public void shouldReturnPetById() throws Exception {

        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_PET_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/pet/" + MOCK_PET_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petType").value(MOCK_PET_TYPE_1.name()))
                .andExpect(jsonPath("$.name").value(MOCK_PET_NAME_1))
                .andExpect(jsonPath("$.sex").value(MOCK_PET_SEX_1.name()))
                .andExpect(jsonPath("$.age").value(MOCK_PET_AGE_1))
                .andExpect(jsonPath("$.isFreeStatus").value(MOCK_PET_IS_FREE_STATUS_1));
    }

    @Test
    public void shouldDeletePet() throws Exception {

        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_PET_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .delete("/pet/" + MOCK_PET_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllPets() throws Exception {
        when(petRepository.findAll()).thenReturn(MOCK_PETS);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/pet/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_PETS)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода списка питомцев
        // в результате вызова эндпоинта "/pet/all" должен вернуться список питомцев - MOCK_PETS
    }

    @Test
    public void shouldReturnAllByShelterId() throws Exception {

        MOCK_PET_1.setShelter(MOCK_SHELTER_1);
        MOCK_PET_2.setShelter(MOCK_SHELTER_2);
        MOCK_PET_3.setShelter(MOCK_SHELTER_2);

        when(petRepository.findAllByShelterId(any(Integer.class))).thenReturn(MOCK_DOGS);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/pet/all-pets-by-shelter/" + MOCK_SHELTER_2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_DOGS)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода установленного списка питомцев
    }

    @Test
    public void shouldReturnAllByShelterIdAndIsFreeStatus() throws Exception {

        MOCK_PET_1.setShelter(MOCK_SHELTER_1);
        MOCK_PET_2.setShelter(MOCK_SHELTER_2);
        MOCK_PET_3.setShelter(MOCK_SHELTER_2);

        when(petRepository.findAllByShelterIdAndIsFreeStatus(any(Integer.class), any(Boolean.class)))
                .thenReturn(MOCK_CATS);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/pet/all-free-pets-by-shelter?shelterId=" + MOCK_SHELTER_1.getId() +
                                "&isFreeStatus=" + true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_CATS)));
    }


    @Test
    public void shouldReturnAllByAdopterId() throws Exception {

        MOCK_PET_1.setAdopter(MOCK_ADOPTER_1);
        MOCK_PET_2.setAdopter(MOCK_ADOPTER_2);
        MOCK_PET_3.setAdopter(MOCK_ADOPTER_2);

        when(petRepository.findAllByAdopterId(any(Long.class)))
                .thenReturn(MOCK_DOGS);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/pet/all-pets-by-adopter/" + MOCK_ADOPTER_2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_DOGS)));
    }


}
