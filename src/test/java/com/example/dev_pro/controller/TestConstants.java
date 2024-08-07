package com.example.dev_pro.controller;

import com.example.dev_pro.model.Volunteer;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final Long MOCK_VOLUNTEER_ID_1 = 1L;
    public static final String MOCK_VOLUNTEER_NICK_NAME_1 = "Volunteer nickName 1";
    public static final String MOCK_VOLUNTEER_LAST_NAME_1 = "Volunteer lastName 1";
    public static final String MOCK_VOLUNTEER_FIRST_NAME_1 = "Volunteer firstName 1";
    public static final String MOCK_VOLUNTEER_MIDDL_NAME_1 = "Volunteer middleName 1";
    public static final Long MOCK_VOLUNTEER_CHAT_ID_1 = 5555555551L;

    public static final Volunteer MOCK_VOLUNTEER_1 = new Volunteer(
            MOCK_VOLUNTEER_ID_1,
            MOCK_VOLUNTEER_NICK_NAME_1,
            MOCK_VOLUNTEER_LAST_NAME_1,
            MOCK_VOLUNTEER_FIRST_NAME_1,
            MOCK_VOLUNTEER_MIDDL_NAME_1,
            MOCK_VOLUNTEER_CHAT_ID_1

    );

    public static final Long MOCK_VOLUNTEER_ID_2 = 2L;
    public static final String MOCK_VOLUNTEER_NICK_NAME_2 = "Volunteer nickName 2";
    public static final String MOCK_VOLUNTEER_LAST_NAME_2 = "Volunteer lastName 2";
    public static final String MOCK_VOLUNTEER_FIRST_NAME_2 = "Volunteer firstName 2";
    public static final String MOCK_VOLUNTEER_MIDDL_NAME_2 = "Volunteer middleName 2";
    public static final Long MOCK_VOLUNTEER_CHAT_ID_2 = 5555555552L;

    public static final Volunteer MOCK_VOLUNTEER_2 = new Volunteer(
            MOCK_VOLUNTEER_ID_2,
            MOCK_VOLUNTEER_NICK_NAME_2,
            MOCK_VOLUNTEER_LAST_NAME_2,
            MOCK_VOLUNTEER_FIRST_NAME_2,
            MOCK_VOLUNTEER_MIDDL_NAME_2,
            MOCK_VOLUNTEER_CHAT_ID_2

    );

    public static final Long MOCK_VOLUNTEER_ID_3 = 3L;
    public static final String MOCK_VOLUNTEER_NICK_NAME_3 = "Volunteer nickName 3";
    public static final String MOCK_VOLUNTEER_LAST_NAME_3 = "Volunteer lastName 3";
    public static final String MOCK_VOLUNTEER_FIRST_NAME_3 = "Volunteer firstName 3";
    public static final String MOCK_VOLUNTEER_MIDDL_NAME_3 = "Volunteer middleName 3";
    public static final Long MOCK_VOLUNTEER_CHAT_ID_3 = 5555555553L;

    public static final Volunteer MOCK_VOLUNTEER_3 = new Volunteer(
            MOCK_VOLUNTEER_ID_3,
            MOCK_VOLUNTEER_NICK_NAME_3,
            MOCK_VOLUNTEER_LAST_NAME_3,
            MOCK_VOLUNTEER_FIRST_NAME_3,
            MOCK_VOLUNTEER_MIDDL_NAME_3,
            MOCK_VOLUNTEER_CHAT_ID_3
    );

    public static final Long MOCK_VOLUNTEER_ID_4 = 4L;
    public static final String MOCK_VOLUNTEER_NICK_NAME_4 = "Volunteer nickName 4";
    public static final String MOCK_VOLUNTEER_LAST_NAME_4 = "Volunteer lastName 4";
    public static final String MOCK_VOLUNTEER_FIRST_NAME_4 = "Volunteer firstName 4";
    public static final String MOCK_VOLUNTEER_MIDDL_NAME_4 = "Volunteer middleName 4";
    public static final Long MOCK_VOLUNTEER_CHAT_ID_4 = 5555555554L;

    public static final Volunteer MOCK_VOLUNTEER_4 = new Volunteer(
            MOCK_VOLUNTEER_ID_4,
            MOCK_VOLUNTEER_NICK_NAME_4,
            MOCK_VOLUNTEER_LAST_NAME_4,
            MOCK_VOLUNTEER_FIRST_NAME_4,
            MOCK_VOLUNTEER_MIDDL_NAME_4,
            MOCK_VOLUNTEER_CHAT_ID_4
    );

    public static final String MOCK_VOLUNTEER_NEW_LAST_NAME = "Volunteer new lastName";

    public static final List<Volunteer> MOCK_VOLUNTEERS = new ArrayList<>() {{
        add(MOCK_VOLUNTEER_1);
        add(MOCK_VOLUNTEER_2);
        add(MOCK_VOLUNTEER_3);
        add(MOCK_VOLUNTEER_4);
    }};

    public static final List<String> MOCK_VOLUNTEER_NICK_NAMES = new ArrayList<>() {{
        add(MOCK_VOLUNTEER_NICK_NAME_1);
        add(MOCK_VOLUNTEER_NICK_NAME_2);
        add(MOCK_VOLUNTEER_NICK_NAME_3);
        add(MOCK_VOLUNTEER_NICK_NAME_4);
    }};

}
