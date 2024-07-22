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
    @Value("${telegram.bot.infoMsgAboutDogShelter}")
    private String infoMsgDogShelter;

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
