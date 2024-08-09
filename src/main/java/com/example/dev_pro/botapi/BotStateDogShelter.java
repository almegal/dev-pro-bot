package com.example.dev_pro.botapi;

public enum BotStateDogShelter {

    INFO_COM,
    OVERVIEW_COM,
    ADDRESS_COM,
    CAR_PASS_COM,
    SAFETY_RULES_COM,
    ASK_PERSONAL_DATA,
    // Запрос от бота о введении своих данных в определенном формате
    FILLING_PROFILE,
    // Оставление пользователем своих данных для связи
    PROFILE_FILLED,
    // Когда пользователем введены данные, бота можно переключить в состояние PROFILE_FILLED
    COME_BACK_COM,
    TAKE_COM,
    REPORT_COM,
    RECOMMENDATIONS_COM,
    RECOMM_FOR_TRANSPORTING_THE_ANIMAL,
    TO_SET_UP_HOME_FOR_PUPPY,
    SETTING_UP_HOME_FOR_AN_ADULT_PET,
    PROVIDING_HOME_FOR_ANIMAL_DISABILITY,
    ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL,
    CONTACT_DETAILS_HANDLER;

}
