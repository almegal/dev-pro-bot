package com.example.dev_pro.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.DeleteMyCommands;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    @Value("${telegram.bot.startMsg}")
    private String startMsg;

    @Value("${telegram.bot.infoMsg}")
    private String infoMsg;
    
    @Value("${telegram.bot.infoMsgAboutCatShelter}")
    private String infoMsgCatShelter;

    @Value("${telegram.bot.overviewMsgAboutCatShelter}")
    private String overviewMsgCatShelter;

    @Value("${telegram.bot.addressMsgCatShelter}")
    private String addressMsgCatShelter;

    @Value("${telegram.bot.directionsMsgCatShelter}")
    private String directionsMsgCatShelter;

    @Value("${telegram.bot.carPassMsgCatShelter}")
    private String carPassMsgCatShelter;

    @Value("${telegram.bot.safetyRulesMsgCatShelter}")
    private String safetyRulesMsgCatShelter;

    @Value("${telegram.bot.userContactMsgCatShelter}")
    private String userContactMsgCatShelter;

    @Value("${telegram.bot.comeBackMsgCatShelter}")
    private String comeBackMsgCatShelter;

    @Value("${telegram.bot.infoMsgAboutDogShelter}")
    private String infoMsgDogShelter;

    @Value("${telegram.bot.overviewMsgAboutDogShelter}")
    private String overviewMsgDogShelter;

    @Value("${telegram.bot.addressMsgDogShelter}")
    private String addressMsgDogShelter;

    @Value("${telegram.bot.directionsMsgDogShelter}")
    private String directionsMsgDogShelter;

    @Value("${telegram.bot.carPassMsgDogShelter}")
    private String carPassMsgDogShelter;

    @Value("${telegram.bot.safetyRulesMsgCatShelter}")
    private String safetyRulesMsgDogShelter;

    @Value("${telegram.bot.userContactMsgCatShelter}")
    private String userContactMsgDogShelter;

    @Value("${telegram.bot.comeBackMsgDogShelter}")
    private String comeBackMsgDogShelter;

    @Value("${telegram.bot.takeMsg}")
    private String takeMsg;

    @Value("${telegram.bot.callVolunteerMsg}")
    private String callVolunteerMsg;

    @Value("${telegram.bot.errorMsg}")
    private String errorMsg;

    @Value("${telegram.bot.messageToVolunteerMsg}")
    private String messageToVolunteerMsg;

    /**
     * Создание и настройка Telegram-бота.
     *
     * @return новый экземпрляр TelegramBot.
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(TELEGRAM_BOT_TOKEN);
        //  очищает команды
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
