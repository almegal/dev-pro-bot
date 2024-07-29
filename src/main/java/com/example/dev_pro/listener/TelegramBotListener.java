package com.example.dev_pro.listener;

import com.example.dev_pro.service.CallbackService;
import com.example.dev_pro.service.CommandHandlerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TelegramBotListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final CommandHandlerService commandHandlerService;
    private final CallbackService callbackService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .filter(Objects::nonNull)
                .forEach(update -> {
                    try {
                        handleUpdate(update);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //обрабатываем в зависимости от того какой update пришел
    private void handleUpdate(Update update) {
        // если  сообщение содержит одно из значений
        if (update.message().text() == null) {
            return;
        }
        String text = update.message().text();
        switch (text) {
            case "Cat":
            case "Dog":
                callbackService.handleCallback(update);
                break;
            default:
                commandHandlerService.commandProcessing(update);
                break;
        }
    }
}
