package com.example.dev_pro.listener;

import com.example.dev_pro.service.CommandHandlerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramBotListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final CommandHandlerService commandHandlerService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .filter(update -> update != null)
                .forEach(update -> {
                    try {
                        commandHandlerService.commandProcessing(update);
                    } catch (Exception e) {

                    }
                });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
