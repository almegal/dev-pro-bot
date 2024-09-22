package com.example.dev_pro.controller;

import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.example.dev_pro.model.*;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.example.dev_pro.controller.ReportTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PetService petService;

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldCreatePet() throws Exception {

        ResponseEntity<Pet> newPetRs = restTemplate.postForEntity(
                "http://localhost:" + port + "/pet",
                MOCK_PET_1,
                Pet.class
        );

        assertThat(newPetRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Pet pet = newPetRs.getBody();

        assertThat(pet.getPetType()).isEqualTo(MOCK_PET_1.getPetType());
        assertThat(pet.getName()).isEqualTo(MOCK_PET_1.getName());
        assertThat(pet.getSex()).isEqualTo(MOCK_PET_1.getSex());
        assertThat(pet.getAge()).isEqualTo(MOCK_PET_1.getAge());
        assertThat(pet.getIsFreeStatus()).isEqualTo(MOCK_PET_1.getIsFreeStatus());
    }

    @Test
    public void shouldUpdatePet() throws Exception {

        Pet createdPet = createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1,
                MOCK_PET_IS_FREE_STATUS_1);
        createdPet.setAge(MOCK_PET_NEW_AGE);

        restTemplate.put(
                "http://localhost:" + port + "/pet",
                createdPet,
                Pet.class
        );

        ResponseEntity<Pet> getPetRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/pet/" + createdPet.getId(),
                Pet.class
        );

        assertThat(getPetRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Pet pet = getPetRs.getBody();
        assertThat(pet.getAge()).isEqualTo(createdPet.getAge());
    }

    @Test
    public void shouldReturnPetById() throws Exception {

        Pet createdPet = createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1,
                MOCK_PET_IS_FREE_STATUS_1);

        ResponseEntity<Pet> getPetRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/pet/" + createdPet.getId(),
                Pet.class
        );

        assertThat(getPetRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Pet pet = getPetRs.getBody();

        assertThat(pet.getPetType()).isEqualTo(createdPet.getPetType());
        assertThat(pet.getName()).isEqualTo(createdPet.getName());
        assertThat(pet.getSex()).isEqualTo(createdPet.getSex());
        assertThat(pet.getAge()).isEqualTo(createdPet.getAge());
        assertThat(pet.getIsFreeStatus()).isEqualTo(createdPet.getIsFreeStatus());
    }

    @Test
    public void shouldDeletePet() throws Exception {

        // Для данного теста необходимо использовать идентификатор питомца, отличный от иных используемых идентификаторов
        Pet createdPet = createMockPet(4L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1,
                MOCK_PET_IS_FREE_STATUS_1);

        restTemplate.delete(
                "http://localhost:" + port + "/pet/" + createdPet.getId(),
                Pet.class
        );

        ResponseEntity<Pet> getPetRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/pet/" + createdPet.getId(),
                Pet.class
        );

        assertThat(getPetRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnAllPets() throws Exception {

        createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1,
                MOCK_PET_IS_FREE_STATUS_1);
        createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2,
                MOCK_PET_IS_FREE_STATUS_2);
        createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3,
                MOCK_PET_IS_FREE_STATUS_3);

        List<Pet> pets = restTemplate.exchange(
                "http://localhost:" + port + "/pet/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {}
        ).getBody();

        assertNotNull(pets);
        assertEquals(pets.size(), MOCK_PETS.size());
        // В связи с тем, что запуск тестов может осуществляться непоследовательно, то несмотря на то, что поля
        // adopter и shelter инициализируются ниже, они могут попасть в коллекцию питомцев MOCK_PETS уже сейчас.
        // Но у сущности Pet над полями 'adopter' и 'shelter' имеется аннотация @JsonIgnore, поэтому актуальный
        // объект Pet будет иметь поле adopter == null и поле shelter == null.
        // Поэтому в данном случае необходимо сравнивать не коллекции объектов между собой,
        // а поля какого-то одного из объектов или всех объектов; при этом поля, у которых имеется аннотация @JsonIgnore,
        // должны быть равны null

        assertThat(pets.get(0).getPetType()).isEqualTo(MOCK_PET_TYPE_1);
        assertThat(pets.get(0).getName()).isEqualTo(MOCK_PET_NAME_1);
        assertThat(pets.get(0).getAge()).isEqualTo(MOCK_PET_AGE_1);
        assertThat(pets.get(0).getSex()).isEqualTo(MOCK_PET_SEX_1);
        assertThat(pets.get(0).getIsFreeStatus()).isEqualTo(MOCK_PET_IS_FREE_STATUS_1);
        assertThat(pets.get(0).getShelter()).isEqualTo(null);
        assertThat(pets.get(0).getAdopter()).isEqualTo(null);
        // также можно проверить поля второго и третьего объектов
    }

    @Test
    public void shouldReturnAllByShelterId() throws Exception {

        Shelter createdShelter1 = shelterService.createShelter(MOCK_SHELTER_1);
        Shelter createdShelter2 = shelterService.createShelter(MOCK_SHELTER_2);

        // Поле shelter проинициализировали, но из-за аннотации @JsonIgnore актуальный результат у этого поля
        // все-равно будет равным null
        MOCK_PET_1.setShelter(createdShelter1);
        MOCK_PET_2.setShelter(createdShelter2);
        MOCK_PET_3.setShelter(createdShelter2);

        petService.createPet(MOCK_PET_1);
        petService.createPet(MOCK_PET_2);
        petService.createPet(MOCK_PET_3);

        List<Pet> expected = Arrays.asList(MOCK_PET_2, MOCK_PET_3);

        List<Pet> pets = restTemplate.exchange(
                "http://localhost:" + port + "/pet/all-pets-by-shelter/" + createdShelter2.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {}
        ).getBody();

        assertNotNull(pets);
        assertEquals(pets.size(), expected.size());

        // В связи с тем, что у сущности Pet над полем 'Adopter adopter' имеется аннотация @JsonIgnore, то
        // актуальный результат Pet будет иметь поле adopter == null
        // В то время как мы ожидаем поле adopter проинициализированным
        // Поэтому в данном случае необходимо сравнивать не коллекции объектов между собой,
        // а поля какого-то одного из объектов, при этом поля, у которых имеется аннотация @JsonIgnore, должны быть
        // равны null

        assertThat(pets.get(0).getPetType()).isEqualTo(expected.get(0).getPetType());
        assertThat(pets.get(0).getName()).isEqualTo(expected.get(0).getName());
        assertThat(pets.get(0).getAge()).isEqualTo(expected.get(0).getAge());
        assertThat(pets.get(0).getSex()).isEqualTo(expected.get(0).getSex());
        assertThat(pets.get(0).getIsFreeStatus()).isEqualTo(expected.get(0).getIsFreeStatus());
        assertThat(pets.get(0).getShelter()).isEqualTo(null);
        // также можно проверить поля второго объекта
    }

    @Test
    public void shouldReturnAllByShelterIdAndIsFreeStatus() throws Exception {

        Shelter createdShelter1 = shelterService.createShelter(MOCK_SHELTER_1);
        Shelter createdShelter2 = shelterService.createShelter(MOCK_SHELTER_2);

        MOCK_PET_1.setShelter(createdShelter1);
        MOCK_PET_2.setShelter(createdShelter2);
        MOCK_PET_3.setShelter(createdShelter2);

        petService.createPet(MOCK_PET_1);
        petService.createPet(MOCK_PET_2);
        petService.createPet(MOCK_PET_3);

        List<Pet> pets1 = restTemplate.exchange(
                "http://localhost:" + port + "/pet/all-free-pets-by-shelter?shelterId=" + createdShelter1.getId() +
                        "&isFreeStatus=" + true,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {}
        ).getBody();

        assertNotNull(pets1);
        assertEquals(pets1.size(), MOCK_CATS.size());
        //assertThat(pets1).isEqualTo(MOCK_CATS);

        List<Pet> pets2 = restTemplate.exchange(
                "http://localhost:" + port + "/pet/all-free-pets-by-shelter?shelterId=" + createdShelter1.getId() +
                        "&isFreeStatus=" + false,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {}
        ).getBody();

        assertNotEquals(pets2.size(), MOCK_CATS.size());
    }

    @Test
    public void shouldReturnAllByAdopterId() throws Exception {

        Pet createdPet1 = createMockPet(1L, MOCK_PET_TYPE_1, MOCK_PET_NAME_1, MOCK_PET_SEX_1, MOCK_PET_AGE_1,
                MOCK_PET_IS_FREE_STATUS_1);
        Pet createdPet2 = createMockPet(2L, MOCK_PET_TYPE_2, MOCK_PET_NAME_2, MOCK_PET_SEX_2, MOCK_PET_AGE_2,
                MOCK_PET_IS_FREE_STATUS_2);
        Pet createdPet3 = createMockPet(3L, MOCK_PET_TYPE_3, MOCK_PET_NAME_3, MOCK_PET_SEX_3, MOCK_PET_AGE_3,
                MOCK_PET_IS_FREE_STATUS_3);

        List<Long> petIds1 = Collections.singletonList(createdPet1.getId());
        List<Long> petIds2 = Arrays.asList(createdPet2.getId(), createdPet3.getId());

        TelegramUser createdUser1 = telegramUserRepository.save(MOCK_USER_1);
        TelegramUser createdUser2 = telegramUserRepository.save(MOCK_USER_2);

        MOCK_ADOPTER_DTO_1.setTelegramUserId(createdUser1.getId());
        MOCK_ADOPTER_DTO_1.setPetIds(petIds1);
        MOCK_ADOPTER_DTO_2.setTelegramUserId(createdUser2.getId());
        MOCK_ADOPTER_DTO_2.setPetIds(petIds2);

        Adopter createdAdopter1 = adopterService.create(MOCK_ADOPTER_DTO_1);
        Adopter createdAdopter2 = adopterService.create(MOCK_ADOPTER_DTO_2);

        MOCK_PET_1.setAdopter(createdAdopter1);
        MOCK_PET_2.setAdopter(createdAdopter2);
        MOCK_PET_3.setAdopter(createdAdopter2);

        petService.updatePet(MOCK_PET_1);
        petService.updatePet(MOCK_PET_2);
        petService.updatePet(MOCK_PET_3);

        List<Pet> pets = restTemplate.exchange(
                "http://localhost:" + port + "/pet/all-pets-by-adopter/" + createdAdopter2.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {}
        ).getBody();

        assertNotNull(pets);
        assertEquals(pets.size(), MOCK_DOGS.size());
        //assertThat(pets).isEqualTo(MOCK_DOGS);
    }


    // Вспомогательный метод
    public Pet createMockPet(Long id, PetType petType, String name, Sex sex, Integer age, Boolean isFreeStatus) {
        return petService.createPet(new Pet(id, petType, name, sex, age, isFreeStatus));
    }

}
