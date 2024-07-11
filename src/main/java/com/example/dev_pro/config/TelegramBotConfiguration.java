package com.example.dev_pro.config;

import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.startMsg}")
    private String startMsg;

    @Value("${telegram.bot.infoMsg}")
    private String infoMsg;

    @Value("${telegram.bot.takeMsg}")
    private String takeMsg;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot telegramBot = new TelegramBot(botToken);
        return telegramBot;
    }
}
