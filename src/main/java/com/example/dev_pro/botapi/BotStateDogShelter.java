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
    REPORT_COM;
}
