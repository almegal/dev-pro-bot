package com.example.dev_pro.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.DeleteMyCommands;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(TELEGRAM_BOT_TOKEN);
        SetMyCommands setMyCommands = new SetMyCommands(
                  new BotCommand("/start", "Начать использование бота")
                , new BotCommand("/info", "Получение информации о приюте")
                , new BotCommand("/take", "Получение инструкции по опеке над животным")
                , new BotCommand("/call", "Вызов волонтера")
        );
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
