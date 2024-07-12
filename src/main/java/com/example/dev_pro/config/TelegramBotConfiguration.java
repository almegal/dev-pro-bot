package com.example.dev_pro.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.DeleteMyCommands;
import com.pengrad.telegrambot.request.SetMyCommands;
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

    @Value("${telegram.bot.takeMsg}")
    private String takeMsg;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(TELEGRAM_BOT_TOKEN);
        SetMyCommands setMyCommands = new SetMyCommands(
                  new BotCommand("/start", startMsg)
                , new BotCommand("/info", infoMsg)
                , new BotCommand("/take", takeMsg)
        );
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
