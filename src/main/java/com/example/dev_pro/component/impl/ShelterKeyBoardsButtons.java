package com.example.dev_pro.component.impl;

import com.example.dev_pro.component.Buttons;
import com.pengrad.telegrambot.model.request.*;
import org.springframework.stereotype.Component;


@Component
public class ShelterKeyBoardsButtons implements Buttons {
 
    public static final String INFO_COM = "/info";
    public static final String TAKE_COM = "/take";
    public static final String REPORT_COM = "/report";
    public static final String CALL_COM = "/call";
    public static final String MAIN_COME_BACK_COM = "/main_come_back";

    public static final String USER_CONTACT_COM = "/user_contact";
    public static final String COME_BACK_COM = "/come_back";

    public static final String OVERVIEW_COM = "/overview";
    public static final String ADDRESS_COM = "/address";
    public static final String CAR_PASS_COM = "/car_pass";
    public static final String SAFETY_RULES_COM = "/safety_rules";

    public static final String LIST_ANIMALS_COM = "/list_animals";
    public static final String MEETING_ANIMALS_COM = "/meeting_animals";
    public static final String LIST_DOCUMENTS_COM = "/list_documents";
    public static final String RECOMMENDATIONS_COM = "/recommendations";
    public static final String REASONS_REFUSAL_COM = "/reasons_refusal";
    public static final String TAKE_COME_BACK_COM = "/come_back";

    public static final String RECOMM_FOR_TRANSPORTING_THE_ANIMAL = "/RecommForTransportingTheAnimal";
    public static final String TO_SET_UP_HOME_FOR_PUPPY = "/ToSetUpHomeForPuppy";
    public static final String SETTING_UP_HOME_FOR_AN_ADULT_PET = "/SettingUpHomeForAnAdultPet";
    public static final String PROVIDING_HOME_FOR_ANIMAL_DISABILITY = "/ProvidingHomeForAnimalDisability";
    public static final String ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL = "/AdviceHandlerInitialCommunAnimal";
    public static final String CONTACT_DETAILS_HANDLER = "/ContactDetailsHandler";
    public static final String RECOMM_COME_BACK_COM = "/recomm_come_back";

    public static final String REPORT_FORMAT="/report_format";
    public static final String SEND_REPORT="/send_report";
    public static final String REPORT_COME_BACK_COM = "/report_come_back";


    @Override
    public Keyboard getKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(INFO_COM),
                        new KeyboardButton(TAKE_COM),
                        new KeyboardButton(REPORT_COM),
                        new KeyboardButton(CALL_COM)
                }
        ).resizeKeyboard(true);
    }

    @Override
    public Keyboard getInfoKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[][]{
                        {new KeyboardButton(OVERVIEW_COM), new KeyboardButton(ADDRESS_COM),
                                new KeyboardButton(CAR_PASS_COM)},
                        {new KeyboardButton(SAFETY_RULES_COM), new KeyboardButton(USER_CONTACT_COM),
                                new KeyboardButton(COME_BACK_COM)}
                }
        ).resizeKeyboard(true);
    }

    @Override
    public Keyboard getTakeKeyboardButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[][]{
                        {new KeyboardButton(LIST_ANIMALS_COM)},
                        {new KeyboardButton(MEETING_ANIMALS_COM), new KeyboardButton(LIST_DOCUMENTS_COM),
                                new KeyboardButton(RECOMMENDATIONS_COM)},
                        {new KeyboardButton(REASONS_REFUSAL_COM ), new KeyboardButton(USER_CONTACT_COM),
                                new KeyboardButton(COME_BACK_COM)}
                }
        ).resizeKeyboard(true);
    }

    @Override
    public Keyboard getRecommendationsButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[][]{
                        {new KeyboardButton(RECOMM_FOR_TRANSPORTING_THE_ANIMAL)},
                        {new KeyboardButton(TO_SET_UP_HOME_FOR_PUPPY), new KeyboardButton(SETTING_UP_HOME_FOR_AN_ADULT_PET),
                                new KeyboardButton(PROVIDING_HOME_FOR_ANIMAL_DISABILITY)},
                        {new KeyboardButton(ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL), new KeyboardButton(CONTACT_DETAILS_HANDLER),
                                new KeyboardButton(RECOMM_COME_BACK_COM)}
                }
        ).resizeKeyboard(true);
    }

    public Keyboard getReportButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[][]{
                        {new KeyboardButton(REPORT_FORMAT), new KeyboardButton(SEND_REPORT)},
                        {new KeyboardButton(REPORT_COME_BACK_COM)},
                }
        ).resizeKeyboard(true);
    }


}
