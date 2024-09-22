package com.example.dev_pro.controller;


import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.dev_pro.controller.VolunteerTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VolunteerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldCreateVolunteer() throws Exception {

        ResponseEntity<Volunteer> newVolunteerRs = restTemplate.postForEntity(
                "http://localhost:" + port + "/volunteer",
                MOCK_VOLUNTEER_1,
                Volunteer.class
        );

        assertThat(newVolunteerRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Volunteer newVolunteer = newVolunteerRs.getBody();

        assertThat(newVolunteer.getChatId()).isEqualTo(MOCK_VOLUNTEER_1.getChatId());
        assertThat(newVolunteer.getNickName()).isEqualTo(MOCK_VOLUNTEER_1.getNickName());
        assertThat(newVolunteer.getLastName()).isEqualTo(MOCK_VOLUNTEER_1.getLastName());
        assertThat(newVolunteer.getFirstName()).isEqualTo(MOCK_VOLUNTEER_1.getFirstName());
        assertThat(newVolunteer.getMiddleName()).isEqualTo(MOCK_VOLUNTEER_1.getMiddleName());

    }

    @Test
    public void shouldReturnVolunteerById() throws Exception {

        Volunteer createdVolunteer = createMockVolunteer();

        ResponseEntity<Volunteer> getVolunteerRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/volunteer/" + createdVolunteer.getId(),
                Volunteer.class
        );

        assertThat(getVolunteerRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Volunteer volunteer = getVolunteerRs.getBody();

        assertThat(volunteer.getChatId()).isEqualTo(createdVolunteer.getChatId());
        assertThat(volunteer.getNickName()).isEqualTo(createdVolunteer.getNickName());
        assertThat(volunteer.getLastName()).isEqualTo(createdVolunteer.getLastName());
        assertThat(volunteer.getFirstName()).isEqualTo(createdVolunteer.getFirstName());
        assertThat(volunteer.getMiddleName()).isEqualTo(createdVolunteer.getMiddleName());
    }

    @Test
    public void shouldDeleteVolunteer() throws Exception {

        Volunteer createdVolunteer = createMockVolunteer();

        restTemplate.delete(
                "http://localhost:" + port + "/volunteer/" + createdVolunteer.getId(),
                Volunteer.class
        );

        ResponseEntity<Volunteer> getVolunteerRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/volunteer/" + createdVolunteer.getId(),
                Volunteer.class
        );

        assertThat(getVolunteerRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldUpdateVolunteer() throws Exception {

        Volunteer createdVolunteer = createMockVolunteer();
        createdVolunteer.setLastName(MOCK_VOLUNTEER_NEW_LAST_NAME);

        restTemplate.put(
                "http://localhost:" + port + "/volunteer",
                createdVolunteer,
                Volunteer.class
        );

        ResponseEntity<Volunteer> getVolunteerRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/volunteer/" + createdVolunteer.getId(),
                Volunteer.class
        );

        assertThat(getVolunteerRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Volunteer volunteer = getVolunteerRs.getBody();
        assertThat(volunteer.getLastName()).isEqualTo(createdVolunteer.getLastName());
    }

    @Test
    public void shouldReturnAllVolunteers() throws Exception {

        createMockVolunteer(1l, MOCK_VOLUNTEER_CHAT_ID_1, MOCK_VOLUNTEER_NICK_NAME_1, MOCK_VOLUNTEER_LAST_NAME_1,
                MOCK_VOLUNTEER_FIRST_NAME_1, MOCK_VOLUNTEER_MIDDLE_NAME_1);
        createMockVolunteer(2l, MOCK_VOLUNTEER_CHAT_ID_2, MOCK_VOLUNTEER_NICK_NAME_2, MOCK_VOLUNTEER_LAST_NAME_2,
                MOCK_VOLUNTEER_FIRST_NAME_2, MOCK_VOLUNTEER_MIDDLE_NAME_2);
        createMockVolunteer(3l, MOCK_VOLUNTEER_CHAT_ID_3, MOCK_VOLUNTEER_NICK_NAME_3, MOCK_VOLUNTEER_LAST_NAME_3,
                MOCK_VOLUNTEER_FIRST_NAME_3, MOCK_VOLUNTEER_MIDDLE_NAME_3);
        createMockVolunteer(4l, MOCK_VOLUNTEER_CHAT_ID_4, MOCK_VOLUNTEER_NICK_NAME_4, MOCK_VOLUNTEER_LAST_NAME_4,
                MOCK_VOLUNTEER_FIRST_NAME_4, MOCK_VOLUNTEER_MIDDLE_NAME_4);

        List<Volunteer> volunteers = restTemplate.exchange(
                "http://localhost:" + port + "/volunteer/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Volunteer>>() {}
        ).getBody();

        assertFalse(volunteers.isEmpty());
        assertTrue(volunteers.size() == MOCK_VOLUNTEERS.size());
        assertThat(volunteers).isEqualTo(MOCK_VOLUNTEERS);
    }

    @Test
    public void shouldReturnListNickNamesOfVolunteers() throws Exception {

        createMockVolunteer(1l, MOCK_VOLUNTEER_CHAT_ID_1, MOCK_VOLUNTEER_NICK_NAME_1, MOCK_VOLUNTEER_LAST_NAME_1,
                MOCK_VOLUNTEER_FIRST_NAME_1, MOCK_VOLUNTEER_MIDDLE_NAME_1);
        createMockVolunteer(2l, MOCK_VOLUNTEER_CHAT_ID_2, MOCK_VOLUNTEER_NICK_NAME_2, MOCK_VOLUNTEER_LAST_NAME_2,
                MOCK_VOLUNTEER_FIRST_NAME_2, MOCK_VOLUNTEER_MIDDLE_NAME_2);
        createMockVolunteer(3l, MOCK_VOLUNTEER_CHAT_ID_3, MOCK_VOLUNTEER_NICK_NAME_3, MOCK_VOLUNTEER_LAST_NAME_3,
                MOCK_VOLUNTEER_FIRST_NAME_3, MOCK_VOLUNTEER_MIDDLE_NAME_3);
        createMockVolunteer(4l, MOCK_VOLUNTEER_CHAT_ID_4, MOCK_VOLUNTEER_NICK_NAME_4, MOCK_VOLUNTEER_LAST_NAME_4,
                MOCK_VOLUNTEER_FIRST_NAME_4, MOCK_VOLUNTEER_MIDDLE_NAME_4);

        List <String> nickNames = restTemplate.exchange(
                "http://localhost:" + port + "/volunteer/nicknames",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();

        assertTrue(nickNames.size() == MOCK_VOLUNTEER_NICK_NAMES.size());
        assertThat(nickNames).isEqualTo(MOCK_VOLUNTEER_NICK_NAMES);
    }



    public Volunteer createMockVolunteer() {
        return volunteerService.createVolunteer(MOCK_VOLUNTEER_1);
    }

    public Volunteer createMockVolunteer(Long id, Long chatId, String nickName, String lastName, String firstName,
                                         String middleName) {
        return volunteerService.createVolunteer(new Volunteer(id, chatId, nickName, lastName, firstName, middleName));
    }

}
