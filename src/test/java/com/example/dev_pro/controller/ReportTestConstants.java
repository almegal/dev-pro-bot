package com.example.dev_pro.controller;

import com.example.dev_pro.dto.AdopterDTO;
import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.example.dev_pro.enums.ShelterType;
import com.example.dev_pro.model.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ReportTestConstants {

    /**
     * Приюты
     */

    public static final Integer MOCK_SHELTER_ID_1 = 1;
    public static final String MOCK_SHELTER_NAME_1 = "Shelter name 1";
    public static final ShelterType MOCK_SHELTER_TYPE_1 = ShelterType.CAT_SHELTER;
    public static final String MOCK_SHELTER_ADDRESS_1 = "Shelter address 1";
    public static final String MOCK_SHELTER_TIME_WORK_1 = "Shelter time work 1";
    public static final String MOCK_SHELTER_PHONE_1 = "Shelter phone 1";
    public static final String MOCK_SHELTER_SECURITY_PHONE_1 = "Shelter security phone 1";

    public static final Shelter MOCK_SHELTER_1 = new Shelter(
            MOCK_SHELTER_ID_1,
            MOCK_SHELTER_NAME_1,
            MOCK_SHELTER_TYPE_1,
            MOCK_SHELTER_ADDRESS_1,
            MOCK_SHELTER_TIME_WORK_1,
            MOCK_SHELTER_PHONE_1,
            MOCK_SHELTER_SECURITY_PHONE_1
    );

    public static final Integer MOCK_SHELTER_ID_2 = 2;
    public static final String MOCK_SHELTER_NAME_2 = "Shelter name 2";
    public static final ShelterType MOCK_SHELTER_TYPE_2 = ShelterType.DOG_SHELTER;
    public static final String MOCK_SHELTER_ADDRESS_2 = "Shelter address 2";
    public static final String MOCK_SHELTER_TIME_WORK_2 = "Shelter time work 2";
    public static final String MOCK_SHELTER_PHONE_2 = "Shelter phone 2";
    public static final String MOCK_SHELTER_SECURITY_PHONE_2 = "Shelter security phone 2";

    public static final Shelter MOCK_SHELTER_2 = new Shelter(
            MOCK_SHELTER_ID_2,
            MOCK_SHELTER_NAME_2,
            MOCK_SHELTER_TYPE_2,
            MOCK_SHELTER_ADDRESS_2,
            MOCK_SHELTER_TIME_WORK_2,
            MOCK_SHELTER_PHONE_2,
            MOCK_SHELTER_SECURITY_PHONE_2
    );


    /**
     * Питомцы
     */

    public static final Long MOCK_PET_ID_1 = 1L;
    public static final PetType MOCK_PET_TYPE_1 = PetType.CAT;
    public static final String MOCK_PET_NAME_1 = "Cat name 1";
    public static final Sex MOCK_PET_SEX_1 = Sex.MAN;
    public static final Integer MOCK_PET_AGE_1 = 1;
    public static final Boolean MOCK_PET_IS_FREE_STATUS_1 = true;

    public static final Pet MOCK_PET_1 = new Pet(
            MOCK_PET_ID_1,
            MOCK_PET_TYPE_1,
            MOCK_PET_NAME_1,
            MOCK_PET_SEX_1,
            MOCK_PET_AGE_1,
            MOCK_PET_IS_FREE_STATUS_1
    );

    public static final Long MOCK_PET_ID_2 = 2L;
    public static final PetType MOCK_PET_TYPE_2 = PetType.DOG;
    public static final String MOCK_PET_NAME_2 = "Dog name 2";
    public static final Sex MOCK_PET_SEX_2 = Sex.MAN;
    public static final Integer MOCK_PET_AGE_2 = 2;
    public static final Boolean MOCK_PET_IS_FREE_STATUS_2 = false;

    public static final Pet MOCK_PET_2 = new Pet(
            MOCK_PET_ID_2,
            MOCK_PET_TYPE_2,
            MOCK_PET_NAME_2,
            MOCK_PET_SEX_2,
            MOCK_PET_AGE_2,
            MOCK_PET_IS_FREE_STATUS_2
    );

    public static final Long MOCK_PET_ID_3 = 3L;
    public static final PetType MOCK_PET_TYPE_3 = PetType.DOG;
    public static final String MOCK_PET_NAME_3 = "Dog name 3";
    public static final Sex MOCK_PET_SEX_3 = Sex.WOMAN;
    public static final Integer MOCK_PET_AGE_3 = 3;
    public static final Boolean MOCK_PET_IS_FREE_STATUS_3 = false;

    public static final Pet MOCK_PET_3 = new Pet(
            MOCK_PET_ID_3,
            MOCK_PET_TYPE_3,
            MOCK_PET_NAME_3,
            MOCK_PET_SEX_3,
            MOCK_PET_AGE_3,
            MOCK_PET_IS_FREE_STATUS_3
    );

    public static final Integer MOCK_PET_NEW_AGE = 5;

    public static final List<Pet> MOCK_CATS = new ArrayList<>() {{
        add(MOCK_PET_1);
    }};

    public static final List<Pet> MOCK_DOGS = new ArrayList<>() {{
        add(MOCK_PET_2);
        add(MOCK_PET_3);
    }};

    public static final List<Pet> MOCK_PETS = new ArrayList<>() {{
        add(MOCK_PET_1);
        add(MOCK_PET_2);
        add(MOCK_PET_3);
    }};

    public static final List<Pet> MOCK_PETS_iS_FREE_STATUS = new ArrayList<>() {{
        add(MOCK_PET_1);
    }};


    /**
     * Пользователи телеграм
     */

    public static final Long MOCK_USER_ID_1 = 1L;
    public static final Long MOCK_USER_TELEGRAM_ID_1 = 1L ;
    public static final Long MOCK_USER_CHAT_ID_1 = 1L;
    public static final String MOCK_USER_NICK_NAME_1 = "User nick name 1";
    public static final String MOCK_USER_LAST_NAME_1 = "User last name 1";
    public static final String MOCK_USER_FIRST_NAME_1 = "User first name 1";
    public static final String MOCK_USER_MIDDLE_NAME_1 = "User middle name 1";
    public static final String MOCK_USER_PHONE_NUMBER_1 = "User phone number 1";
    public static final String MOCK_USER_CAR_NUMBER_1 = "User car number 1";
    public static final String MOCK_USER_SHELTER_1 = "User shelter cat";

    public static final TelegramUser MOCK_USER_1 = new TelegramUser(
            MOCK_USER_ID_1,
            MOCK_USER_TELEGRAM_ID_1,
            MOCK_USER_CHAT_ID_1,
            MOCK_USER_NICK_NAME_1,
            MOCK_USER_LAST_NAME_1,
            MOCK_USER_FIRST_NAME_1,
            MOCK_USER_MIDDLE_NAME_1,
            MOCK_USER_PHONE_NUMBER_1,
            MOCK_USER_CAR_NUMBER_1,
            MOCK_USER_SHELTER_1
    );

    public static final Long MOCK_USER_ID_2 = 2L;
    public static final Long MOCK_USER_TELEGRAM_ID_2 = 2L ;
    public static final Long MOCK_USER_CHAT_ID_2 = 2L;
    public static final String MOCK_USER_NICK_NAME_2 = "User nick name 2";
    public static final String MOCK_USER_LAST_NAME_2 = "User last name 2";
    public static final String MOCK_USER_FIRST_NAME_2 = "User first name 2";
    public static final String MOCK_USER_MIDDLE_NAME_2 = "User middle name 2";
    public static final String MOCK_USER_PHONE_NUMBER_2 = "User phone number 2";
    public static final String MOCK_USER_CAR_NUMBER_2 = "User car number 2";
    public static final String MOCK_USER_SHELTER_2 = "User shelter dog";

    public static final TelegramUser MOCK_USER_2 = new TelegramUser(
            MOCK_USER_ID_2,
            MOCK_USER_TELEGRAM_ID_2,
            MOCK_USER_CHAT_ID_2,
            MOCK_USER_NICK_NAME_2,
            MOCK_USER_LAST_NAME_2,
            MOCK_USER_FIRST_NAME_2,
            MOCK_USER_MIDDLE_NAME_2,
            MOCK_USER_PHONE_NUMBER_2,
            MOCK_USER_CAR_NUMBER_2,
            MOCK_USER_SHELTER_2
    );


    /**
     * Усыновители
     */

    public static final Long MOCK_ADOPTER_ID_1 = 1L;
    public static final boolean MOCK_ADOPTER_PROBATION_PERIOD_1 = false;

    public static final Adopter MOCK_ADOPTER_1 = new Adopter(
            MOCK_ADOPTER_ID_1,
            MOCK_ADOPTER_PROBATION_PERIOD_1
    );

    public static final Long MOCK_ADOPTER_ID_2 = 2L;
    public static final boolean MOCK_ADOPTER_PROBATION_PERIOD_2 = false;

    public static final Adopter MOCK_ADOPTER_2 = new Adopter(
            MOCK_ADOPTER_ID_2,
            MOCK_ADOPTER_PROBATION_PERIOD_2
    );


    /**
     * Усыновители DTO
     */

    public static final AdopterDTO MOCK_ADOPTER_DTO_1 = new AdopterDTO();
    public static final AdopterDTO MOCK_ADOPTER_DTO_2 = new AdopterDTO();


    /**
     * Отчеты
     */

    public static final Integer MOCK_REPORT_ID_1 = 1;
    public static final LocalDate MOCK_REPORT_DATE_1 = LocalDate.of(2024, Month.AUGUST, 27);
    public static final String MOCK_REPORT_TEXT_1 = "Report text 1";
    public static final String MOCK_REPORT_PATH_FILE_1 = "Report path file 1";
    public static final long MOCK_REPORT_FILE_SIZE_1 = 1;
    public static final String MOCK_REPORT_MEDIA_TYPE_1 = "Report media type 1";
    public static final Boolean MOCK_REPORT_IS_VIEWED_1 = true;

    public static final Report MOCK_REPORT_1 = new Report(
            MOCK_REPORT_ID_1,
            MOCK_REPORT_DATE_1,
            MOCK_REPORT_TEXT_1,
            MOCK_REPORT_PATH_FILE_1,
            MOCK_REPORT_FILE_SIZE_1,
            MOCK_REPORT_MEDIA_TYPE_1,
            MOCK_REPORT_IS_VIEWED_1
    );

    public static final Integer MOCK_REPORT_ID_2 = 2;
    public static final LocalDate MOCK_REPORT_DATE_2 = LocalDate.of(2024, Month.AUGUST, 28);
    public static final String MOCK_REPORT_TEXT_2 = "Report text 2";
    public static final String MOCK_REPORT_PATH_FILE_2 = "Report path file 2";
    public static final long MOCK_REPORT_FILE_SIZE_2 = 2;
    public static final String MOCK_REPORT_MEDIA_TYPE_2 = "Report media type 2";
    public static final Boolean MOCK_REPORT_IS_VIEWED_2 = null;

    public static final Report MOCK_REPORT_2 = new Report(
            MOCK_REPORT_ID_2,
            MOCK_REPORT_DATE_2,
            MOCK_REPORT_TEXT_2,
            MOCK_REPORT_PATH_FILE_2,
            MOCK_REPORT_FILE_SIZE_2,
            MOCK_REPORT_MEDIA_TYPE_2,
            MOCK_REPORT_IS_VIEWED_2
    );

    public static final Integer MOCK_REPORT_ID_3 = 3;
    public static final LocalDate MOCK_REPORT_DATE_3 = LocalDate.of(2024, Month.AUGUST, 28);
    public static final String MOCK_REPORT_TEXT_3 = "Report text 3";
    public static final String MOCK_REPORT_PATH_FILE_3 = "Report path file 3";
    public static final long MOCK_REPORT_FILE_SIZE_3 = 3;
    public static final String MOCK_REPORT_MEDIA_TYPE_3 = "Report media type 3";
    public static final Boolean MOCK_REPORT_IS_VIEWED_3 = null;

    public static final Report MOCK_REPORT_3 = new Report(
            MOCK_REPORT_ID_3,
            MOCK_REPORT_DATE_3,
            MOCK_REPORT_TEXT_3,
            MOCK_REPORT_PATH_FILE_3,
            MOCK_REPORT_FILE_SIZE_3,
            MOCK_REPORT_MEDIA_TYPE_3,
            MOCK_REPORT_IS_VIEWED_3
    );

    public static final String MOCK_REPORT_NEW_TEXT = "Report new text";

    public static final List<Report> MOCK_REPORTS_BY_IS_VIEWED_NULL = new ArrayList<>() {{
        add(MOCK_REPORT_2);
        add(MOCK_REPORT_3);
    }};

    public static final List<Report> MOCK_REPORTS_BY_ADOPTER_1 = new ArrayList<>() {{
        add(MOCK_REPORT_1);
    }};

    public static final List<Report> MOCK_REPORTS_BY_ADOPTER_2_AND_PET_2 = new ArrayList<>() {{
        add(MOCK_REPORT_2);
    }};


}
